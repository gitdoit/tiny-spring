package org.seefly.tinyioc.step6;

/**
 * bean的引用描述
 * 用于解决bean之间相互依赖的问题
 * @author liujianxin
 * @date 2019/9/3 17:41
 */
public class BeanReference {
    private String name;
    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
