package org.seefly.tinyioc.step4.factory;


import org.seefly.tinyioc.step4.BeanDefinition;

/**
 * @author liujianxin
 */
public interface BeanFactory {
    Object getBean(String beanName);

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception;
}
