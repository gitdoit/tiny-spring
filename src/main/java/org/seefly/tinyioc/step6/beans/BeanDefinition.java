package org.seefly.tinyioc.step6.beans;


/**
 * @author liujianxin
 * @date 2019/9/3 9:53
 */
public class BeanDefinition {
    private Object bean;
    private String beanClassName;
    private Class beanClass;
    private PropertyValues propertyValues = new PropertyValues();

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
