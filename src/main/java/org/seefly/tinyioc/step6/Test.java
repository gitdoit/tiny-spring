package org.seefly.tinyioc.step6;

import org.seefly.tinyioc.step6.context.ClassPathXmlApplicationContext;

/**
 * 之前的容器已经有了该有的基本功能，但是用起来有点麻烦
 * 这里使用代理模式来解决这种问题。
 *
 * ApplicationContext 是一个标记接口，继承了BeanFactory
 *     BeanFactory的定义只是规定了bean的装配和获取，但是并没有
 *  规定这些bean的定义信息如何获取。所以这个接口的功能是代理BeanFactory
 *  同时增强一个功能就是获取bean的定义信息。
 *
 * AbstractApplicationContext 抽象实现类
 * 内部包含一个BeanFactory成员，标准的代理模式玩法。
 * getBean直接调用BeanFactory的。同时提供一个refresh的空方法
 * 给子类来实现从具体地方加载bean的定义信息的实现。
 *
 * ClassPathXmlApplicationContext 具体实现类
 * 覆盖了refresh方法，并且利用XmlBeanDefinitionReader来解析UrlResourceLoader
 * 读取到的Resource，获取BeanDefinition，然后保存到BeanFactory
 *
 * @author liujianxin
 * @date 2019/9/3 19:50
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc6.xml");
        HelloWorld helloWorld = (HelloWorld)applicationContext.getBean("hello");
        helloWorld.sayHello();
    }
}
