package org.seefly.tinyioc.aop.aop;

/**
 * 切点，用于筛选连接点
 * @author liujianxin
 */
public interface Pointcut {


    MethodMatcher getMethodMatcher();

    ClassFilter getClassFilter();
}
