package org.seefly.tinyioc.step7.beans.xml;

import org.seefly.tinyioc.step7.BeanReference;
import org.seefly.tinyioc.step7.beans.AbstractBeanDefinitionReader;
import org.seefly.tinyioc.step7.beans.BeanDefinition;
import org.seefly.tinyioc.step7.beans.PropertyValue;
import org.seefly.tinyioc.step7.beans.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * 一个从XML中读取配置文件的读取器
 *
 * @author liujianxin
 * @date 2019/9/3 11:18
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }



    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        // 获取资源
        InputStream inputStream = this.getResourceLoader().getResource(location).getInputStream();
        // 解析资源
        doLoadBeanDefinitions(inputStream);
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document parse = documentBuilder.parse(inputStream);
        // 解析XML
        registerBeanDefinitions(parse);
        inputStream.close();
    }

    private void registerBeanDefinitions(Document doc){
        Element documentElement = doc.getDocumentElement();

        parseBeanDefinitions(documentElement);
    }

    private void parseBeanDefinitions(Element root){
        // 遍历元素，每个元素对应一个BeanDefinition
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if(item instanceof Element){
                Element ele = (Element) item;
                processBeanDefinition(ele);
            }
        }
    }

    private void processBeanDefinition(Element ele) {
        // 读取属性
        String name = ele.getAttribute("name");
        String clazzName = ele.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        processProperty(ele,beanDefinition);
        beanDefinition.setBeanClassName(clazzName);
        getRegistry().put(name,beanDefinition);
    }

    private void processProperty(Element ele, BeanDefinition beanDefinition){
        // 读取配置
        NodeList propertyNode = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                PropertyValue pair;
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if(value != null && value.length() > 0){
                    // 简单值
                    pair = new PropertyValue(name,value);
                }else if((value = propertyEle.getAttribute("ref")) != null) {
                    // 对象引用
                    pair = new PropertyValue(name,new BeanReference(value));
                }else {
                    throw new IllegalStateException("Can not found this value for key "+ name);
                }
                beanDefinition.getPropertyValues().addPropertyValue(pair);
            }
        }

    }
}

