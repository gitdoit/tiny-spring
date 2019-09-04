package org.seefly.tinyioc.aop.beans.factory;



import org.seefly.tinyioc.aop.beans.BeanDefinition;
import org.seefly.tinyioc.aop.beans.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liujianxin
 * @date 2019/9/3 10:00
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    /**缓存bean的定义信息*/
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    /**已注册的bean的名字*/
    private List<String> beanDefinitionNames = new ArrayList<>();
    /**后置处理器*/
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();


    @Override
    public Object getBean(String beanName) throws Exception {
        // getBean之前必须先要注册bean的信息
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){
            throw new IllegalStateException("Can not find bean"+ beanName);
        }
        Object bean = beanDefinition.getBean();
        // 如果在获取bean的时候再去创建就能解决bean之间相互依赖的问题
        if(bean == null){
            bean = doCreateBean(beanDefinition);
            // 应用bean的后置处理器，以及调用初始化方法
            bean = initializeBean(bean,beanName);
            // 创建的bean实例放到bean的定义中去
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    /**
     * 创建bean实例
     */
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object beanInstance = createBeanInstance(beanDefinition);
        // 给创建的bean装配属性
        applyPropertyValues(beanInstance,beanDefinition);
        return beanInstance;
    }

    /**
     * 模板方法，具体实现交由子类去做
     * 配置bean的属性
     */
    protected void applyPropertyValues(Object bean,BeanDefinition beanDefinition) throws Exception {}

    /**
     * 初始化bean
     * 调用后置处理器-前
     * 调用初始化方法
     * 调用后置处理器-后
     */
    protected Object initializeBean(Object bean,String name) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean,name);
        }

        //TODO 假装这里调用了初始化方法

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean,name);
        }
        return bean;
    }

    /**
     * 执行创建实例的方法
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition){
        try {
            return beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为了解决bean之间的循环依赖问题
     * bean的创建改为lazy-init方式，不再一注册的时候就创建
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
        beanDefinitionNames.add(beanName);
    }

    /**
     * 主动实例化单例非懒加载的bean
     */
    public void perInstanceSingletons() throws Exception {
        for (String beanDefinitionName : beanDefinitionNames) {
            getBean(beanDefinitionName);
        }
    }

    /**
     * 添加bean的后置处理器
     */
    public void addBeanPostProcessor(BeanPostProcessor postProcessor){
        this.beanPostProcessors.add(postProcessor);
    }

    /**
     * 获取容器中指定类型的bean
     */
    public <T> List<T>  getBeansForType(Class<T> type) throws Exception {
        List<T> beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if(type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())){
                beans.add((T)getBean(beanDefinitionName));
            }
        }
        return beans;
    }



}
