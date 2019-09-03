package org.seefly.tinyioc.step1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liujianxin
 * @date 2019/9/3 0:25
 */
public class BeanFactory {
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String beanName){
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        return beanDefinition.getBean();
    }

    public void registerBeanDefinition(String beanName,BeanDefinition beanDefinition){
        this.beanDefinitionMap.put(beanName,beanDefinition);
    }
}
