package org.seefly.tinyioc.step7.aop;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author liujianxin
 * @date 2019/9/3 23:31
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    private Object target;
    private Method method;
    private Object[] args;


    public ReflectiveMethodInvocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    /**
     * spring中同名的类的做法就是
     * 维护一个MethodInterceptor列表
     * 然后递归遍历，这里就直接引用目标方法了
     */
    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target,args);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
