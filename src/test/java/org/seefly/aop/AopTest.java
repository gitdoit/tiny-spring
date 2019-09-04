package org.seefly.aop;

import org.junit.Assert;
import org.junit.Test;
import org.seefly.tinyioc.aop.HelloWorld;
import org.seefly.tinyioc.aop.aop.AspectJExpressionPointcut;

/**
 * @author liujianxin
 * @date 2019/9/4 10:09
 */
public class AopTest {

    @Test
    public void testExpression() throws NoSuchMethodException {
        // 指定包下所有类的所有方法
        String expression = "execution(* org.seefly.tinyioc.aop.*.say*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean sayHello = aspectJExpressionPointcut.getMethodMatcher().matches(HelloWorld.class.getDeclaredMethod("sayHello"), HelloWorld.class);
        Assert.assertTrue(sayHello);
    }
}
