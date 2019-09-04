package org.seefly.tinyioc.aop.aop;

/**
 * 代理创建工厂
 * spring源码中是使用策略模式，根据代理对象的不同
 * 来选择不同的动态代理方式，如JDK代理或者CGLIB代理
 *
 * @author liujianxin
 * @date 2019/9/4 18:14
 */
public class ProxyFactory extends AdvisedSupport implements AopProxy {

    /**
     * 这又是代理模式
     *
     */
    @Override
    public Object getProxy() {
        return createAopProxy().getProxy();
    }


    /**
     * spring中这里是策略模式
     */
    protected AopProxy createAopProxy(){
        return new CglibAopProxy(this);
    }
}
