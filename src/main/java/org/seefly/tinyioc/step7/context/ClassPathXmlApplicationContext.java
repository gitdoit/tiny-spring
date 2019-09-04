package org.seefly.tinyioc.step7.context;

import org.seefly.tinyioc.step7.beans.factory.AbstractBeanFactory;
import org.seefly.tinyioc.step7.beans.factory.AutowireCapableBeanFactory;
import org.seefly.tinyioc.step7.beans.io.UrlResourceLoader;
import org.seefly.tinyioc.step7.beans.xml.XmlBeanDefinitionReader;

/**
 * @author liujianxin
 * @date 2019/9/3 19:38
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    private String location;

    public ClassPathXmlApplicationContext(AbstractBeanFactory beanFactory, String location) throws Exception {
        super(beanFactory);
        this.location = location;
        refresh();
    }

    public ClassPathXmlApplicationContext(String location) throws Exception {
        this(new AutowireCapableBeanFactory(),location);
    }

    @Override
    public void refresh() throws Exception {
        // 加载定义信息
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(new UrlResourceLoader());
        reader.loadBeanDefinitions(location);
        // 将加载的bean定义信息注册容器中
        reader.getRegistry().forEach((name,beanDefinition) -> beanFactory.registerBeanDefinition(name,beanDefinition));
        // 初始化单例
        beanFactory.perInstanceSingletons();
    }
}
