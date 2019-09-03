package org.seefly.tinyioc.step2.factory;

import org.seefly.tinyioc.step2.BeanDefinition;

/**
 * @author liujianxin
 */
public interface BeanFactory {

    /**
     * 从容器中获取bean
     * @param beanName bean的名称
     * @return bean实例
     */
    Object getBean(String beanName);

    /**
     * 注册bean的信息
     * @param beanName bean名称
     * @param beanDefinition bean的定义信息
     */
    void registerBeanDefinition(String beanName,BeanDefinition beanDefinition);
}
