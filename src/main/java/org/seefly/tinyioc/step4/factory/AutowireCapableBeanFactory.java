package org.seefly.tinyioc.step4.factory;


import org.seefly.tinyioc.step4.BeanDefinition;
import org.seefly.tinyioc.step4.PropertyValue;
import org.seefly.tinyioc.step4.PropertyValues;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 可自动装备的容器实现
 * 将bean的创建和属性的装配拆开
 *
 * @author liujianxin
 * @date 2019/9/3 10:03
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {


    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object beanInstance = createBeanInstance(beanDefinition);
        applyPropertyValues(beanInstance,beanDefinition);
        return beanInstance;
    }

    /**
     * 执行创建实例的方法
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition){
        try {
            return beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将定义的参数对应用到实例上
     * Spring的做法是通过set方法来做的，这里为了方便就用反射
     */
    protected void applyPropertyValues(Object bean,BeanDefinition mdb) throws Exception {
        PropertyValues propertyValues = mdb.getPropertyValues();
        List<PropertyValue> values = propertyValues.getValues();
        for(PropertyValue value : values){
            Field field = bean.getClass().getDeclaredField(value.getName());
            field.setAccessible(true);
            field.set(bean,value.getValue());
        }
    }
}
