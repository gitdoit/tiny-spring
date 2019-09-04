package org.seefly.tinyioc.aop.aop;

/**
 * 用于描述被代理对象
 * @author liujianxin
 * @date 2019/9/3 23:21
 */
public class TargetSource {
    private Class<?>[] targetClass;

    private Object target;

    public TargetSource(Object target, Class<?>... targetClass) {
        this.target = target;
        this.targetClass = targetClass;
    }

    public Class<?>[] getTargetClass() {
        return targetClass;
    }

    public Object getTarget() {
        return target;
    }
}
