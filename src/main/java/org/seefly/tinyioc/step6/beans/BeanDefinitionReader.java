package org.seefly.tinyioc.step6.beans;

/**
 * 加载Bean的定义信息顶级接口
 * @author liujianxin
 */
public interface BeanDefinitionReader {

    /**
     * 通过指定位置加载bean的定义信息到容器中
     * @param location 指定的位置
     */
    void loadBeanDefinitions(String location)throws Exception;
}
