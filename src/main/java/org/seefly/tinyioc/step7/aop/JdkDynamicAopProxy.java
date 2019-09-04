package org.seefly.tinyioc.step7.aop;

import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liujianxin
 * @date 2019/9/3 23:33
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
        return methodInterceptor.invoke(new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(),method,args));
    }



    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{advisedSupport.getTargetSource().getTargetClass()},this);
    }
}
