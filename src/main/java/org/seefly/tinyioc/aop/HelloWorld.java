package org.seefly.tinyioc.aop;

/**
 * @author liujianxin
 * @date 2019/9/3 9:38
 */
public class HelloWorld  implements Say {
    private String name;

    @Override
    public void sayHello(){
        System.out.println("Hello "+name);
    }
}
