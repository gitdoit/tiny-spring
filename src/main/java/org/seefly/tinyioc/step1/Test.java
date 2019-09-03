package org.seefly.tinyioc.step1;

/**
 * Spring IOC的两个基本元素
 * BeanDefinition 就是bean的定义信息，保存了创建bean的一些额外信息，例如是否单例，属性等
 * BeanFactory 容器
 *
 * 实现功能
 * 基本的ioc模型
 *
 *
 * @author liujianxin
 * @date 2019/9/3 15:55
 */
public class Test {
    public static void main(String[] args) {
        // 定义Bean信息
        BeanDefinition beanDefinition = new BeanDefinition(new HelloWorld());

        // 注册到容器
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBeanDefinition("hello",beanDefinition);

        // 从容器中获取实例
        HelloWorld hello = (HelloWorld)beanFactory.getBean("hello");
        hello.sayHello();
    }
}
