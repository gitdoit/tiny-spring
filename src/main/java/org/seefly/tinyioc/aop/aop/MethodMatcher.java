package org.seefly.tinyioc.aop.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器用于切点
 * @author liujianxin
 */
public interface MethodMatcher {
    boolean matches(Method method,Class tagetClass);
}
