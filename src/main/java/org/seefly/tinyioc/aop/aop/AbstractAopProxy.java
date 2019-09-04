package org.seefly.tinyioc.aop.aop;

/**
 * @author liujianxin
 * @date 2019/9/4 18:19
 */
public abstract class AbstractAopProxy implements AopProxy {
    protected AdvisedSupport advisedSupport;

    public AbstractAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }


}
