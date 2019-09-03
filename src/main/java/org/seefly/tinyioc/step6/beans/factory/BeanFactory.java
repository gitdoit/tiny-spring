package org.seefly.tinyioc.step6.beans.factory;


import org.seefly.tinyioc.step5.BeanDefinition;

/**
 * @author liujianxin
 */
public interface BeanFactory {
    Object getBean(String beanName);

}
