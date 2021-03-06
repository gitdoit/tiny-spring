package org.seefly.tinyioc.step5.factory;


import org.seefly.tinyioc.step5.BeanDefinition;

/**
 * @author liujianxin
 */
public interface BeanFactory {
    Object getBean(String beanName);

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception;
}
