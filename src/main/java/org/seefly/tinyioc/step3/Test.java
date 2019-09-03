package org.seefly.tinyioc.step3;

import org.seefly.tinyioc.step3.factory.AutowireCapableBeanFactory;

/**
 * 上一步中我们只对可自动装配的工厂做了简单实现并没有真正的实现可自动装配的功能
 * 这里我们在AutowireCapableBeanFactory中实现了doCreateBean方法
 * 并将创建Bean分为两个步骤
 * 1、newInstance 创建bean
 * 2、applyPropertyValues 装配属性
 *
 * 我们自定义数据结构PropertyValue以及用PropertyValues来组合
 * 之所以自定义一个类来组合不使用列表的原因是因为可以在添加PropertyValue的时候可以做一些校验
 *
 * 实现功能
 * 1、根据BeanDefinition中的配置，自动的装配Bean的属性
 * 2、细化Bean的创建流程分为创建bean和装配bean属性
 *
 * 缺点
 * 我们手动的一个个创建BeanDefinition非常麻烦
 * 可以使用配置文件将这些定义信息放到配置文件中，用的时候直接读取就好了。
 *
 *
 *
 * @author liujianxin
 * @date 2019/9/3 10:16
 */
public class Test {
    public static void main(String[] args) throws Exception {

        // 定义bean的属性信息
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name","seefly"));
        // 定义bean的信息
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName("org.seefly.tinyioc.step3.HelloWorld");
        beanDefinition.setPropertyValues(propertyValues);

        // 创建bean工厂
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        beanFactory.registerBeanDefinition("hello",beanDefinition);

        // 获取实例
        HelloWorld helloWorld = (HelloWorld)beanFactory.getBean("hello");
        helloWorld.sayHello();

    }
}
