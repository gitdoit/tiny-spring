package org.seefly.tinyioc.aop.aop;

/**
 * 基于切点的增强器
 * 切点用于筛选连接点，对于匹配上的连接点织入切面
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

}
