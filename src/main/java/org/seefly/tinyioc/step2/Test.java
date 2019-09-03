package org.seefly.tinyioc.step2;

import org.seefly.tinyioc.step2.factory.AutowireCapableBeanFactory;

/**
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
