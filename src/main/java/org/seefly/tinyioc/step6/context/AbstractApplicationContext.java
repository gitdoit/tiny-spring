package org.seefly.tinyioc.step6.context;

import org.seefly.tinyioc.step6.beans.factory.AbstractBeanFactory;

/**
 * @author liujianxin
 * @date 2019/9/3 19:34
 */
public abstract class AbstractApplicationContext implements ApplicationContext{
    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public  void refresh()throws Exception{};


    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
