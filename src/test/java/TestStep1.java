import org.junit.Test;
import org.seefly.tinyioc.step1.BeanDefinition;
import org.seefly.tinyioc.step1.BeanFactory;

/**
 * Spring IOC的两个基本元素
 * BeanDefinition 就是bean的定义信息，保存了创建bean的一些额外信息，例如是否单例，属性等
 * BeanFactory 容器
 *
 *
 * @author liujianxin
 * @date 2019/9/3 0:17
 */
public class TestStep1 {

    @Test
    public void test(){
        // 定义Bean信息
        BeanDefinition beanDefinition = new BeanDefinition(new HelloWorld());

        // 注册到容器
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBeanDefinition("hello",beanDefinition);

        // 从容器中获取实例
        HelloWorld hello = (HelloWorld)beanFactory.getBean("hello");
        hello.sayHello();
    }

    static class HelloWorld{
        public void sayHello(){
            System.out.println("Hello tiny spring!");
        }
    }

}
