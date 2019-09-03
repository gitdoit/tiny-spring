package org.seefly.tinyioc.step2.factory;

import org.seefly.tinyioc.step2.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一个抽象的bean工厂，定义了BeanFactory的基本结构，但具体如何创建bean实例由子类实现。
 *
 * 在步骤1中，我们是创建好了Bean的实例在放到容器中的，但是在实际的使用中
 * 我们希望容器能够帮我们主动创建Bean的实例。所以这里我们提供了doCreateBean抽象方法
 * 这个方法描述了子类应该如何创建实例对象。
 *
 *
 * @author liujianxin
 * @date 2019/9/3 9:23
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();


    public Object getBean(String beanName) {
        return beanDefinitionMap.get(beanName).getBean();
    }

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
