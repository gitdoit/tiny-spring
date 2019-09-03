package org.seefly.tinyioc.step5.factory;


import org.seefly.tinyioc.step5.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liujianxin
 * @date 2019/9/3 10:00
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();



    @Override
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){
            throw new IllegalStateException("Can not find bean"+ beanName);
        }
        Object bean = beanDefinition.getBean();
        // 如果在获取bean的时候再去创建就能解决bean之间相互依赖的问题
        if(bean == null){
            System.out.println("实例化bean:"+beanName);
            bean = doCreateBean(beanDefinition);
        }
        return bean;
    }

    /**
     * 为了解决bean之间的循环依赖问题
     * bean的创建改为lazy-init方式，不再一注册的时候就创建
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
        beanDefinitionNames.add(beanName);
    }

    /**
     * 主动实例化单例非懒加载的bean
     */
    public void perInstanceSingletons(){
        for (String beanDefinitionName : beanDefinitionNames) {
            getBean(beanDefinitionName);
        }
    }

    protected abstract Object doCreateBean(BeanDefinition beanDefinition) ;
}
