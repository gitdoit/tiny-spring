package org.seefly.tinyioc.step7.beans.io;

import java.net.URL;

/**
 * 实现了通过给定的字符串地址获取URLResource的功能
 * @author liujianxin
 * @date 2019/9/3 11:02
 */
public class UrlResourceLoader implements ResourceLoader {
    @Override
    public UrlResource getResource(String location) {
        URL resource = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(resource);
    }
}
