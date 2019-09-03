package org.seefly.tinyioc.step3.factory;

import org.seefly.tinyioc.step3.BeanDefinition;

/**
 * @author liujianxin
 */
public interface BeanFactory {
    Object getBean(String beanName);

    void registerBeanDefinition(String beanName,BeanDefinition beanDefinition) throws Exception;
}
