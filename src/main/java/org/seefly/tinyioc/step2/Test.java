package org.seefly.tinyioc.step2;

import org.seefly.tinyioc.step2.factory.AutowireCapableBeanFactory;

/**
 *
 *  在步骤1中，我们是创建好了Bean的实例在放到容器中的，但是在实际的使用中我们希望容器能够帮我们主动创建Bean的实例。
 *  所以我们抽BeanFactory作为容器的基础接口，定义了容器两个基本功能
 *  1、注册bean的定义信息
 *  2、从容器中获取bean
 *
 *  同时提供了一个抽象bean工厂用于定义容器的基本结构
 *  1、使用Map保存bean的定义信息
 *  2、实现了获取bean的接口（直接从BeanDefinition中获取）
 *  3、抽取了模板方法doCreateBean交由子类去实现如何具体的创建一个bean
 *
 *  此外还实现了一个简单的可自动装备的容器（还没有真的实现）
 *
 *  实现功能
 *  1、容器的基本结构
 *  2、容器能够根据bean的定义信息自动创建实例
 *
 *  缺点
 *  没法注入bean的属性
 *
 * @author liujianxin
 * @date 2019/9/3 9:34
 */
public class Test {
    public static void main(String[] args) {
        // 创建bean定义信息
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName("org.seefly.tinyioc.step2.HelloWorld");

        // 创建可自动装配的Bean工厂
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        // 注册bean的定义信息，他会帮我们创建Bean，并且还会自动装配属性(当然目前不会)
        beanFactory.registerBeanDefinition("hello",beanDefinition);

        // 从容器中获取Bean实例
        HelloWorld hello = (HelloWorld)beanFactory.getBean("hello");
        hello.sayHello();
    }


}
