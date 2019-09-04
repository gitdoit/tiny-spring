package org.seefly.tinyioc.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author liujianxin
 * @date 2019/9/4 15:52
 */
public class LogInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("执行前...");
        Object proceed = invocation.proceed();
        System.out.println("执行后..");
        return proceed;

    }
}
