package org.seefly.tinyioc.step7.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 包含TargetSource和MethodInterceptor的元数据对象
 * @author liujianxin
 * @date 2019/9/3 23:29
 */
public class AdvisedSupport {

    private TargetSource targetSource;
    private MethodInterceptor methodInterceptor;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }
}
