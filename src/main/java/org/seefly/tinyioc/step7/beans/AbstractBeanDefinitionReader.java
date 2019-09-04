package org.seefly.tinyioc.step7.beans;


import org.seefly.tinyioc.step7.beans.io.ResourceLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定义了BeanDefinitionReader的基本结构
 * @author liujianxin
 * @date 2019/9/3 11:14
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    /**使用资源加载器加载*/
    private ResourceLoader resourceLoader;
    private Map<String, BeanDefinition> registry;

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.registry = new ConcurrentHashMap<>();
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

}
