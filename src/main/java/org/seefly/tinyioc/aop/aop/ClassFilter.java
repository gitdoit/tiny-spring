package org.seefly.tinyioc.aop.aop;

/**
 * 类过滤，用于切点
 * @author liujianxin
 */
public interface ClassFilter {

    boolean matches(Class targetClass);
}
