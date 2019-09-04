package org.seefly.tinyioc.aop.aop;

import org.aopalliance.aop.Advice;

/**
 * 基于AspectJ切点的增强器
 * @author liujianxin
 * @date 2019/9/4 10:04
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    private Advice advice;


    public void setExpression(String expression) {
        this.pointcut.setExpression(expression);
    }


    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
