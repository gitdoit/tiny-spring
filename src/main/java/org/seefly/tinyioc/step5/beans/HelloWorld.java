package org.seefly.tinyioc.step5.beans;

/**
 * @author liujianxin
 * @date 2019/9/3 9:38
 */
public class HelloWorld {
    private String name;
    private Dancer dancer;

    public void sayHello(){
        System.out.println("Hello "+name);
        dancer.dance();
    }
}
