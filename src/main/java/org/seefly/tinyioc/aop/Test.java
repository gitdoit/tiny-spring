package org.seefly.tinyioc.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seefly.tinyioc.aop.aop.AdvisedSupport;
import org.seefly.tinyioc.aop.aop.JdkDynamicAopProxy;
import org.seefly.tinyioc.aop.aop.TargetSource;
import org.seefly.tinyioc.aop.context.ClassPathXmlApplicationContext;

/**
 *
 *
 *
 * @author liujianxin
 * @date 2019/9/3 19:50
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyiocaop.xml");
        HelloWorld helloWorld = (HelloWorld)applicationContext.getBean("hello");
        helloWorld.sayHello();

        // 创建元信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        // 创建代理对象描述
        TargetSource targetSource = new TargetSource(helloWorld,helloWorld.getClass(),Say.class);
        advisedSupport.setTargetSource(targetSource);

        //设置拦截器
        LogInterceptor logInterceptor = new LogInterceptor();
        advisedSupport.setMethodInterceptor(logInterceptor);

        // 创建动态代理
        JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);

        //获取被代理对象
        Say proxy = (Say)jdkDynamicAopProxy.getProxy();

        proxy.sayHello();

    }


    public static class LogInterceptor implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("执行前...");
            Object proceed = invocation.proceed();
            System.out.println("执行后...");
            return proceed;
        }
    }
}
