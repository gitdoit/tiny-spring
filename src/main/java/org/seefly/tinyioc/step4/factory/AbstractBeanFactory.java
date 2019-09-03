package org.seefly.tinyioc.step4.factory;


import org.seefly.tinyioc.step4.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liujianxin
 * @date 2019/9/3 10:00
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();



    @Override
    public Object getBean(String beanName) {
        return beanDefinitionMap.get(beanName).getBean();
    }

    @Override
    public void registerBeanDefinition(String beanName,BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = doCreateBean(beanDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        beanDefinition.setBean(bean);
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    protected abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;
}
