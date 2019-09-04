package org.seefly.tinyioc.aop.aop;

/**
 * 用于描述被代理对象
 * @author liujianxin
 * @date 2019/9/3 23:21
 */
public class TargetSource {
    private Class<?> targetClass;

    private Class<?>[] interfaces;

    private Object target;

    public TargetSource(Object target, Class<?> targetClass,Class<?>... interfaces) {
        this.target = target;
        this.interfaces = interfaces;
        this.targetClass = targetClass;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }

    public Object getTarget() {
        return target;
    }
}
