# TinySpring

> **First of all**
>
> 由于工作中一直在使用Spring框架，所以对它的原理非常感兴趣。从最开始的一路debug，到后来的跟着博客或者视频对这个框架原理有了一些大致的了解。但奈何总感觉是在盲人摸象，茫茫源码总是只能窥其一角。有幸在github上看到了 [code4craft](https://github.com/code4craft/tiny-spring) 的 tinyspring项目，他是从头开始仿照spring一章一章的实现一个功能完整的ioc容器，其中类名以及方法名都能在spring源码中找到原型。所以我就参照该项目，一步一步的照着敲了每一行源码，虽然没有全部掌握，但还是有了不少的收获，在这里做一下总结！

## 项目结构

1. 第一部分非常简单只是给出了一个ioc容器的基本原型 `BeanFactory`  和  `BeanDefinition`
2. 第二部分 `AbstractBeanFactory` 给出了容器的基本结构，并在容器中实现了bean的创建
3. 第三部分 `AutowireCapableBeanFactory` 实现了可自动装配属性，以及 `PropertyValue`对属性的描述。
4. 第四部分实现了类定义信息的读取和解析并引入了 `Resource` `ResourceLoader` `BeanDefinitionReader` 这三个重要组件。
5. 第五部分借助 `BeanReference`来描述Bean之间的引用关系，以此解决了依赖注入的问题。
6. 第六部分使用 `ApplicationContext` 来整合了Bean定义信息的加载，以及初始化容器的操作。
7. AOP部分实现了SpringAop的基本功能

## Ioc容器的实现

### 1、BeanFactory

定义了一个Ioc容器的基本职责，就是通过一个对象名称来获取对象。

```java
public interface BeanFactory {
    Object getBean(String beanName) throws Exception;
}
```

### 2、AbstractBeanFactory

实现了 `BeanFactory`的抽象类，定义了一个Ioc容器的基本结构。使用 `ConcurrentHashMap`来保存Bean定义信息。`getBean(String)` 规定了从容器中获取一个bean的基本逻辑

- 从`Map`中根据Bean的名字获取对象
- 若获取不到则调用 `doCreateBean(BeanDefinition)`方法创建
  - 简单的通过反射创建实例
  - 调用 `applyPropertyValues` 来装配bean实例的属性，该方法交由子类实现
- 创建完成后调用 `initializeBean(Bean,beanName)` 去初始化，然后应用后置处理器

### 3、AutowireCapableBeanFactory

继承 `AbstractBeanFactory`实现了可以自动装配Bean属性的工厂，实现了 `applyPropertyValues`方法，其中逻辑如下

- 获取 `BeanDefinition`中的该实例配置的属性
- 对于简单参数直接调用setter方法
- 对于引用，根据`BeanReference`中的信息来 `getBean(beanName)`获取它的实例，并调用setter方法设值。

经过以上步骤就形成了一个闭环，在初始化一个bean的时候如果发现需要依赖注入其他bean，若被依赖的bean没有初始化，则先去初始化在设值。

### 4、加载类的定义信息

我们知道 `BeanFactory`的定义只是规定了实例的获取，以及创建。但是并没有说明bean的定义信息也就是 `BeanDefinition`是从哪里加载的。Spring的做法是从一个配置文件中读取信息，解析生成容器可以使用的`BeanDefinition`，并注册到 `BeanFactory`。

- `Resource` 对资源文件的一个抽象，提供了 `getInputStream()`方法来获取它描述的资源。
- `ResourceLoader` 一个资源可以在不同的地方，所以有不同的加载方式。所以这个接口提供了一个 `getResource(String)`方法来描述如何加载这个资源。
- `BeanDefinitionReader` 我们可以加载不同类型的资源，如 `xml` 、`json`又或者是 `java`配置文件。它们都是存储类的定义信息的载体，虽然格式不同但最后都会被解析成为 `Ioc`容器需要的 `BeanDefinition`。所以就会有不同的解析方式，这个接口就提供了 `loadBeanDefinition(String)` 来对这些文件进行解析操作。

经过上面三个组件的抽象，我们可以实现对不同位置的不同资源进行不同的加载动作以及执行不同的解析方式。这种设计非常巧妙，我们可以随意的替换不同的组件来应对不同的场景。



### 5、ApplicationContext

上面我们认识到了 `BeanFactory`和资源加载解析，这两个不同的步骤。但是在实际使用的时候还是比较麻烦的，需要手动执行加载，然后再注册到Ioc容器中。所以Spring就提供了 `ApplicationContext`来整合这两个步骤。

```java
public interface ApplicationContext extends BeanFactory {
}
```







### 4、ApplicationContext

### 6、AbstractApplicationContext

可以看到`AbstractApplicationContext`实现了 `ApplicationContext`接口，也就是实现了`BeanFactory`。但是它的构造方法需要提供一个 `BeanFactory`这显然就是代理模式了。通过代理`BeanFactory`对其进行业务扩展，就是提供了加载资源的这个功能。但真正的加载动作 `loadBeanDefinitions(BeanFactory)`是一个抽象方法交给子类去实现的。

```java
public abstract class AbstractApplicationContext implements ApplicationContext {
    protected AbstractBeanFactory beanFactory;
    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    /**
     * 刷新容器
     * 也就是重新加载
     */
    public  void refresh()throws Exception{
        // 加载bean的定义信息
        loadBeanDefinitions(beanFactory);
        // 注册后置处理器,这一步会让后置处理器先创建并放到容器中
        registerBeanPostProcessors(beanFactory);
        // 刷新容器
        onRefresh();
    };
    /**
     * 注册后置处理器
     */
    protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
        // 先从容器中拿出所有的后置处理器，这会导致这些后置处理器被创建
        List<BeanPostProcessor> postProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
        for (BeanPostProcessor postProcessor : postProcessors) {
            beanFactory.addBeanPostProcessor(postProcessor);
        }
    }
    /**
     * 初始化所有单实例的bean
     */
    protected void onRefresh() throws Exception {
        beanFactory.perInstanceSingletons();
    }
    /**
     * 让子类去决定如何加载bean的定义信息
     */
    protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory);
    @Override
    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }
}
```

### 总结

#### ApplicationContext的执行流程

```java
public void refresh()throws Exception{
        // 加载bean的定义信息
        loadBeanDefinitions(beanFactory);
        // 注册后置处理器,这一步会让后置处理器先创建并放到容器中
        registerBeanPostProcessors(beanFactory);
        // 刷新容器
        onRefresh();
    };
```

- `loadBeanDefinitions(beanFactory)` 从指定位置读取配置文件，解析成为 `BeanDefinition`注册到容器
- `registerBeanPostProcessors(beanFactory)` 获取容器中所有类型为`BeanPostPorcessor`，并注册到容器中。这一步会调用到容器的 `getBean(beanName)`方法，会导致后置处理器被创建。
- `onRefresh()`方法会使用`getBean(beanName)`方法对容器中每个单例非懒加载的bean进行初始化。

#### BeanFactory的执行流程

- `getBean(beanName)` 方法从容器中获取一个对象
  - `doCreateBean(beanDefinition)`
    - `createBeanInstance(beanDefinition)` 真正的创建一个对象
    - `applyPropertyValues(beanInstance,beanDefinition)`装配属性
  - `initializeBean(bean,beanName)` 调用后置处理器和初始化方法
    - `postProcessBeforeInitialization(bean,name)`
    - 执行初始化方法
    - `postProcessAfterInitialization(bean,name)`



## AOP的实现

> 关于SpringAop的实现，可能大家都有所了解，就是动态代理。通过“狸猫换太子”的方式对原有对象进行替换来达到增强的目的。我们可以想一下，这个替换可以发生在什么地方？没错，就是在容器初始化创建实例对象的时候，如果发现这个对象需要增强，那么会根据需要选择不同的动态代理方式，JDK或者CGLIB，将编写好的增强逻辑植入到原有对象中，这其中就涉及到了几个问题。

### 1、在哪里增强

首先需要引入下面两个概念来解决这个问题

#### JoinPoint 连接点

连接点就是程序执行过程中可以织入切面的地方，例如方法调用、异常抛出或者是字段的修改。这些地方都可以是连接点。

```java
// 连接点
public interface Joinpoint {
   Object proceed() throws Throwable;  
}
// 一种引用类型的连接点，例如是构造方法或者是普通方法
public interface Invocation extends Joinpoint {
   Object[] getArguments();

}
// 一种方法引用的连接点，Spring只支持到方法引用
public interface MethodInvocation extends Invocation{
    Method getMethod();
}
```



#### Pointcut 切入点

程序中到处都充斥着切入点，但是我们只希望在指定的位置进行增强，那么切入点就是定义如何选择这些连接点的。不要把它理解为被选中的连接点，被选中的连接点还是一个连接点。它更像是一组筛选规则，用来选择指定的切入点进行增强。

```java
public interface Pointcut {
    MethodMatcher getMethodMatcher();
    ClassFilter getClassFilter();
}
```

### 2、要增强什么

#### Advice 通知

​	通过切点选中连接点之后就需要应用我们定义的增强逻辑了，而`Advice`就是我们编写的增强逻辑。

```java
// 标记接口，子类可以实现任何类型的通知，例如是一个拦截器
public interface Advice {
}
// 一种通用的拦截器，可以拦截程序运行期间发生的事件
// 这些事件就是那些连接点，它可以是方法的调用、异常的发生、字段的引用等
public interface Interceptor extends Advice {
}
// 对方法引用的拦截，可以看到invoke方法的参数正是一个连接点的子类-方法引用
// 正如Interceptor中说的一样
public interface MethodInterceptor extends Interceptor {
    // 在这里，也就是在访问连接点的路上，我们进行拦截
    Object invoke(MethodInvocation invocation) throws Throwable;
}
```



### 3、怎么实现

#### Advisor 增强器

这个东西不在AOP联盟的规范中，是Spring自己的定义。Spring认识到大部分的切面是一个`Advice`和一个`Pointcut`的组合，所以它提供了`Advisor`来组合这两个东西。

```java
public interface Advisor {
    Advice getAdvice();
}
public interface PointcutAdvisor extends Advisor {
   Pointcut getPointcut();
   Advice getAdvice();
}
```

#### AopProxy 代理创建

它的职责就是在确定好切点之后创建代理对象。

```java
public interface AopProxy {
    Object getProxy();
}

// 模板方法，没有实现getProxy()。但是提供了创建代理对象所需要的基础资料
public abstract class AbstractAopProxy implements AopProxy {
    // 这个里面包含了创建一个代理对象所需要的资料
    protected AdvisedSupport advisedSupport;
    public AbstractAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }
}

public class AdvisedSupport {
    // 目标资源，包含了需要被代理的对象、该对象实现的接口、以及类型
    private TargetSource targetSource;
    // 通知、也就是我们需要增强的逻辑
    private MethodInterceptor methodInterceptor;
    // 需要对代理对象中的那个方法进行增强呢？
    private MethodMatcher methodMatcher;
}
```

#### 怎样创建一个代理对象

现在我们一切都准备就绪了，我们的程序中有很多个`JoinPoint`可以用来织入增强逻辑，有`Pointcut`对这些连接点做筛选，有`Advice`来承载我们的增强逻辑，有`Advisor`来组合`Advice` `Pointcut`形成一个切面，有`AopProxy`来生成一个代理对象。道理我都懂，但具体该怎么做呢？

##### BeanPostProcessor 后置处理器

我们知道，一个容器中的对象在创建调用初始化方法前后都会应用后置处理器，Spring也是在这一步把目标对象偷偷的替换成了代理对象。

```java
public interface BeanPostProcessor {
	Object postProcessBeforeInitialization(Object bean, String beanName) ;
	Object postProcessAfterInitialization(Object bean, String beanName);
}
```

- 在 `postProcessAfterInitialization(..)`方法中，每个对象经过这里都会检查容器中配置的 `Advisor`是否能够应用在当前的对象上。
- 从`Advisor`中拿出`Ponitcut`使用它的`ClassFilter`判断当前对象是否需要被增强
- 如果需要被增强，则根据对象的类型选择创建JDK代理或者是CGLIB代理。