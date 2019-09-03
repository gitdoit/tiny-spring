package org.seefly.tinyioc.step6.beans.factory;



import org.seefly.tinyioc.step6.BeanReference;
import org.seefly.tinyioc.step6.beans.BeanDefinition;
import org.seefly.tinyioc.step6.beans.PropertyValue;

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
    protected Object doCreateBean(BeanDefinition beanDefinition)  {
        Object beanInstance = createBeanInstance(beanDefinition);
        // 创建的bean实例放到bean的定义中去
        beanDefinition.setBean(beanInstance);
        // 给创建的bean装配属性
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
     * 在装配属性的时候，如果发现引用是一个对象引用
     * 那么会从容器中去获取
     * 如果容器中已经加载创建了，那么就可以直接获取到了
     * 如果没有则创建并初始化这个bean，环环相套
     */
    protected void applyPropertyValues(Object bean, BeanDefinition mdb)  {
        List<PropertyValue> values = mdb.getPropertyValues().getValues();
        for(PropertyValue value : values){
            Field field;
            try {
                field = bean.getClass().getDeclaredField(value.getName());
                field.setAccessible(true);
                Object o = value.getValue();
                // 如果value是一个对象引用，则从容器中获取
                // 这样，如果容器中没有的话，还是会进行创建到这里
                // 一层一层的直到创建完所有的bean
                if(o instanceof BeanReference){
                    BeanReference reference = (BeanReference)o;
                    o = getBean(reference.getName());
                }
                field.set(bean,o);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }
}
