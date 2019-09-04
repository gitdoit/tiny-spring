package org.seefly.tinyioc.aop.context;

import org.seefly.tinyioc.aop.beans.factory.AbstractBeanFactory;

/**
 * @author liujianxin
 * @date 2019/9/3 19:34
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    /**
     * 让子类决定从哪里加载bean的定义信息
     */
    public  void refresh()throws Exception{};


    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
