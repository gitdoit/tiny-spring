package org.seefly.tinyioc.step2.factory;

import org.seefly.tinyioc.step2.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author liujianxin
 * @date 2019/9/3 9:23
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();


    @Override
    public Object getBean(String beanName) {
        return beanDefinitionMap.get(beanName).getBean();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        Object bean = doCreateBean(beanDefinition);
        beanDefinition.setBean(bean);
        this.beanDefinitionMap.put(beanName,beanDefinition);
    }


    /**
     * 如何创建Bean实例
     */
    protected abstract Object doCreateBean(BeanDefinition beanDefinition);
}
