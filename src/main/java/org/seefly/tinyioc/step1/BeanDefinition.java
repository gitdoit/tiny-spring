package org.seefly.tinyioc.step1;


/**
 * @author liujianxin
 * @date 2019/9/3 0:21
 */
public class BeanDefinition {
    private Object  object;
    public BeanDefinition (Object object){
        this.object = object;
    }

    public Object getBean(){
        return this.object;
    }
}
