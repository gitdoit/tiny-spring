package org.seefly.tinyioc.aop.beans.factory;


/**
 * @author liujianxin
 */
public interface BeanFactory {
    Object getBean(String beanName) throws Exception;
}
