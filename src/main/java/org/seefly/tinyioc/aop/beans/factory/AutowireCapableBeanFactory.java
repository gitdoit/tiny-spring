package org.seefly.tinyioc.aop.beans.factory;



import org.seefly.tinyioc.aop.BeanReference;
import org.seefly.tinyioc.aop.beans.BeanDefinition;
import org.seefly.tinyioc.aop.beans.BeanFactoryAware;
import org.seefly.tinyioc.aop.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 可自动装备的容器实现
 * 将bean的创建和属性的装配拆开
 *
 * @author liujianxin
 * @date 2019/9/3 10:03
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {


    /**
     * 在装配属性的时候，如果发现引用是一个对象引用
     * 那么会从容器中去获取
     * 如果容器中已经加载创建了，那么就可以直接获取到了
     * 如果没有则创建并初始化这个bean，环环相套
     */
    @Override
    protected void applyPropertyValues(Object bean, BeanDefinition mdb) throws Exception {
        if(bean instanceof BeanFactoryAware){
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        List<PropertyValue> values = mdb.getPropertyValues().getValues();
        for(PropertyValue pair : values){
            Object setValue;
            if(pair.getValue() instanceof BeanReference){
                BeanReference reference = (BeanReference)pair.getValue();
                setValue = getBean(reference.getName());
            }else {
                setValue = pair.getValue();
            }
            try {
                Method setter = getMethodByName(pair.getName(),setValue.getClass(), mdb.getBeanClass());
                setter.invoke(bean,setValue);
            }catch (NoSuchMethodException e){
                Field declaredField = bean.getClass().getDeclaredField(pair.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, setValue);
            }

        }
    }

    private Method getMethodByName(String fieldName,Class fieldClass,Class targetClass) throws NoSuchMethodException {
        String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
        Method declaredMethod = targetClass.getDeclaredMethod(methodName, fieldClass);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }
}
