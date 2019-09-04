package org.seefly.tinyioc.aop.beans;


import org.seefly.tinyioc.aop.beans.factory.BeanFactory;

/**
 * @author yihua.huang@dianping.com
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
