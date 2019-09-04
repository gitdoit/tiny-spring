package org.seefly.tinyioc.step7.context;

import org.seefly.tinyioc.step7.beans.factory.AbstractBeanFactory;

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
