package org.seefly.tinyioc.step7.aop;

/**
 * 用于描述被代理对象
 * @author liujianxin
 * @date 2019/9/3 23:21
 */
public class TargetSource {
    private Class targetClass;
    private Object target;

    public TargetSource(Class targetClass, Object target) {
        this.targetClass = targetClass;
        this.target = target;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Object getTarget() {
        return target;
    }
}
