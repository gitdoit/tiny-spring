package org.seefly.tinyioc.aop.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.seefly.tinyioc.aop.beans.BeanFactoryAware;
import org.seefly.tinyioc.aop.beans.BeanPostProcessor;
import org.seefly.tinyioc.aop.beans.factory.AbstractBeanFactory;
import org.seefly.tinyioc.aop.beans.factory.BeanFactory;

import java.util.List;

/**
 * @author liujianxin
 * @date 2019/9/4 12:16
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {
    private AbstractBeanFactory beanFactory;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        this.beanFactory = (AbstractBeanFactory)beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        // 判断是否是基础设施，也就是增强器、通知等
        if(bean instanceof AspectJExpressionPointcutAdvisor){
            return bean;
        }
        if(bean instanceof MethodInterceptor){
            return bean;
        }
        // 从容其中拿出所有的增强器，判断是否能够应用到当前bean上
        // 这一步在spring源码中是有缓存的
        List<AspectJExpressionPointcutAdvisor> advisors = beanFactory
                .getBeansForType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            // 这里的做法是只要类匹配上了就创建代理
            // 我看源码上好像是对其中的方法也要匹配，有任何一个匹配上了才创建代理
            if(advisor.getPointcut().getClassFilter().matches(bean.getClass())){

                // 使用代理工厂
                // 代理工厂使用策略模式+代理模式
                // 策略模式用于选择使用JDK还是CGLIB
                // 代理模式是内部使用选中的AopProxy来创建代理
                ProxyFactory proxyFactory = new ProxyFactory();
                proxyFactory.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                TargetSource targetSource = new TargetSource(bean,bean.getClass(),bean.getClass().getInterfaces());
                proxyFactory.setTargetSource(targetSource);

                return proxyFactory.getProxy();
            }
        }
        return bean;
    }
}
