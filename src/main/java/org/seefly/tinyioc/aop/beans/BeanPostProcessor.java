package org.seefly.tinyioc.aop.beans;

public interface BeanPostProcessor {

	Object postProcessBeforeInitialization(Object bean, String beanName) ;

	Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;

}