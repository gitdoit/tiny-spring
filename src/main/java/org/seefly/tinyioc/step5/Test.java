package org.seefly.tinyioc.step5;

import org.seefly.tinyioc.step5.beans.HelloWorld;
import org.seefly.tinyioc.step5.factory.AutowireCapableBeanFactory;
import org.seefly.tinyioc.step5.io.UrlResourceLoader;
import org.seefly.tinyioc.step5.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 *
 * 前面的属性装配只能装配普通的属性，为了能够解决bean之间的依赖，让依赖关系
 * 交由ioc容器来管理，我们这里引入了BeanReference来代表一个bean对另一个bean的引用
 * 在创建bean的时候如果发现属性是一个引用，那么就去容器中获取
 * 如果没有拿到，就调用getBean方法去获取，这一步也会引发这个bean的初始化
 *
 * 现在我们的容器有了一个ioc容器的基本功能
 * 1、根据bean定义信息创建bean的功能
 * 2、能够根据bean的定义信息解决依赖的问题
 * 3、额外的实现了读取配置文件解析成BeanDefinition的功能
 *
 * @author liujianxin
 * @date 2019/9/3 10:53
 */
public class Test {

    public static void main(String[] args) throws Exception {

        // 加载配置信息
        UrlResourceLoader resourceLoader = new UrlResourceLoader();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(resourceLoader);
        //替换一下就能够从json文件中解析bean的定义信息
        reader.loadBeanDefinitions("tinyioc5.xml");
        Map<String, BeanDefinition> registry = reader.getRegistry();

        // 创建容器
        AutowireCapableBeanFactory factory = new AutowireCapableBeanFactory();
        // 注册bean的定义信息
        registry.forEach(factory::registerBeanDefinition);

        // 实例化非懒加载的bean
        factory.perInstanceSingletons();

        //获取Bean
        HelloWorld hello = (HelloWorld)factory.getBean("hello");
        hello.sayHello();

    }

}
