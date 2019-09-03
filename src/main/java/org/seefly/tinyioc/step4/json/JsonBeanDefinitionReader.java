package org.seefly.tinyioc.step4.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.seefly.tinyioc.step4.AbstractBeanDefinitionReader;
import org.seefly.tinyioc.step4.BeanDefinition;
import org.seefly.tinyioc.step4.PropertyValue;
import org.seefly.tinyioc.step4.io.ResourceLoader;

import java.io.*;

/**
 * 一个从json文件中读取配置信息的类
 * @author liujianxin
 * @date 2019/9/3 15:21
 */
public class JsonBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public JsonBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream inputStream = this.getResourceLoader().getResource(location).getInputStream();
        doLoadBeanDefinitions(inputStream);
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null){
            sb.append(line);
        }
        JSONArray definitions = JSONObject.parseArray(sb.toString());
        for (int i = 0; i < definitions.size(); i++) {
            processBeanDefinition(definitions.getJSONObject(i));
        }
    }


    private void processBeanDefinition(JSONObject jsonObject){
        String beanName = jsonObject.getString("beanName");
        String beanClassName = jsonObject.getString("beanClassName");
        JSONArray properties = jsonObject.getJSONArray("properties");

        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        processProperties(beanDefinition,properties);

        this.getRegistry().put(beanName,beanDefinition);
    }

    private void processProperties(BeanDefinition beanDefinition,JSONArray jsonArray){
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject pair = jsonArray.getJSONObject(i);
            pair.forEach((key,value) -> beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(key,value)));
        }
    }
}
