package org.seefly.tinyioc.step6;

import org.seefly.tinyioc.step6.context.ClassPathXmlApplicationContext;

/**
 * 之前的容器已经有了该有的基本功能，但是用起来有点麻烦
 * 这里使用代理模式来解决这种问题。
 *
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
