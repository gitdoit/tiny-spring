package org.seefly.tinyioc.step5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujianxin
 * @date 2019/9/3 9:56
 */
public class PropertyValues {
    private List<PropertyValue> values = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue){
        values.add(propertyValue);
    }

    public void setValues(List<PropertyValue> values) {
    }

    public List<PropertyValue> getValues() {
        return values;
    }
}
