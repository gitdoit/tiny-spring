package org.seefly.tinyioc.step2;

/**
 * @author liujianxin
 * @date 2019/9/3 9:13
 */
public class BeanDefinition {
    private Object bean;
    private String beanClassName;
    private Class beanClass;

    public BeanDefinition(){}

    public void setBean(Object bean){
        this.bean = bean;
    }

    public Object getBean(){
        return bean;
    }



    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
