package org.seefly.tinyioc.aop.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liujianxin
 * @date 2019/9/4 18:23
 */
public class CglibAopProxy extends AbstractAopProxy {

    public CglibAopProxy(AdvisedSupport advisedSupport) {
        super(advisedSupport);
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTargetClass());
        enhancer.setInterfaces(advisedSupport.getTargetSource().getInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));
        return enhancer.create();
    }


    private static class DynamicAdvisedInterceptor implements MethodInterceptor{
        private AdvisedSupport advised;
        private org.aopalliance.intercept.MethodInterceptor delegateMethodInterceptor;

        private DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
            this.delegateMethodInterceptor = advised.getMethodInterceptor();
        }



        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            if(isMatched(method)){
                // 匹配上了就执行切面逻辑
                return delegateMethodInterceptor.invoke(new CglibMethodInvocation(advised.getTargetSource().getTarget(),method,args,methodProxy));
            }
            // 否则直接执行目标方法
            return new CglibMethodInvocation(advised.getTargetSource().getTarget(),method,args,methodProxy).proceed();
        }

        private boolean isMatched(Method method){
            MethodMatcher methodMatcher = advised.getMethodMatcher();
            return methodMatcher == null || methodMatcher.matches(method, advised.getTargetSource().getTargetClass());
        }
    }


    private static class CglibMethodInvocation extends ReflectiveMethodInvocation{
        private final MethodProxy methodProxy;

        CglibMethodInvocation(Object target, Method method, Object[] args, MethodProxy methodProxy) {
            super(target, method, args);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(target,args);
        }
    }
}
