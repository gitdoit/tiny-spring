package org.seefly.tinyioc.step4;

import org.seefly.tinyioc.step4.factory.AutowireCapableBeanFactory;
import org.seefly.tinyioc.step4.io.UrlResourceLoader;
import org.seefly.tinyioc.step4.json.JsonBeanDefinitionReader;

import java.util.Map;

/**
 * 为了解决步骤3中的需要手动定义Bean的定义信息的问题，我们可以将
 * bean的定义信息放到文件中，需要的时候读取即可。为了实现这个功能我们定义了
 * 以下几个模型用于实现该功能。
 * 1、Resource 用于描述一个资源，提供getInputStream()
 * 2、ResourceLoader 有了对资源的抽象还需要有一种方式去加载这个资源，它就是资源加载器提供
 * 3、BeanDefinitionReader 对资源进行解析，得到我们想要的BeanDefinition
 *
 * 经过上面三个接口的抽象，我们可以实现该接口实现一个来描述Url资源
 * 1、定义UrlResource描述Url资源
 * 2、定义UrlResourceLoader来实现加载动作
 * 3、定义XmlBeanDefinitionReader、JsonBeanDefinitionReader来解析资源获取我们需要的定义信息
 * 我们可以随意替换这三个组件来实现从不同的地方加载不同的资源进行不同的解析。卧槽，真牛逼
 *
 * 到目前为止容器看起来已经像个样子了，但是我们给bean中注入的只有普通参数
 * 没有解决bean之间的依赖关系啊
 *
 * @author liujianxin
 * @date 2019/9/3 10:53
 */
public class Test {

    public static void main(String[] args) throws Exception {

        // 加载配置信息
        UrlResourceLoader resourceLoader = new UrlResourceLoader();
        //XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(resourceLoader);
        //替换一下就能够从json文件中解析bean的定义信息
        JsonBeanDefinitionReader reader = new JsonBeanDefinitionReader(resourceLoader);
        reader.loadBeanDefinitions("tinyioc.json");
        Map<String, BeanDefinition> registry = reader.getRegistry();

        // 创建容器
        AutowireCapableBeanFactory factory = new AutowireCapableBeanFactory();
        // 注册bean的定义信息
        registry.forEach(factory::registerBeanDefinition);

        //获取Bean
        HelloWorld hello = (HelloWorld)factory.getBean("hello");
        hello.sayHello();

    }

}
