package org.seefly.tinyioc.step2.factory;

import org.seefly.tinyioc.step2.BeanDefinition;

/**
 * 可自动装配的容器工厂实现，目前只是简单实现
 * @author liujianxin
 * @date 2019/9/3 9:31
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            return beanClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
