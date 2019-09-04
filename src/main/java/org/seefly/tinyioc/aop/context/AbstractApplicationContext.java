package org.seefly.tinyioc.aop.context;

import org.seefly.tinyioc.aop.beans.BeanPostProcessor;
import org.seefly.tinyioc.aop.beans.factory.AbstractBeanFactory;

import java.util.List;

/**
 * @author liujianxin
 * @date 2019/9/3 19:34
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    /**
     * 刷新容器
     * 也就是重新加载
     */
    public  void refresh()throws Exception{
        // 加载bean的定义信息
        loadBeanDefinitions(beanFactory);
        // 注册后置处理器,这一步会让后置处理器先创建并放到容器中
        registerBeanPostProcessors(beanFactory);
        // 刷新容器
        onRefresh();
    };

    /**
     * 注册后置处理器
     */
    protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
        // 先从容器中拿出所有的后置处理器，这会导致这些后置处理器被创建
        List<BeanPostProcessor> postProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
        for (BeanPostProcessor postProcessor : postProcessors) {
            beanFactory.addBeanPostProcessor(postProcessor);
        }
    }

    /**
     * 初始化所有单实例的bean
     */
    protected void onRefresh() throws Exception {
        beanFactory.perInstanceSingletons();
    }


    /**
     * 让子类去决定如何加载bean的定义信息
     */
    protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception;


    @Override
    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }
}
