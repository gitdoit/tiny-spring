package org.seefly.tinyioc.step3;

import org.seefly.tinyioc.step3.factory.AutowireCapableBeanFactory;

/**
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
