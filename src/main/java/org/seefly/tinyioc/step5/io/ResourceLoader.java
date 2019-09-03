package org.seefly.tinyioc.step5.io;

/**
 * 一个资源加载类，用于获取资源
 * @author liujianxin
 * @date 2019/9/3 11:00
 */
public interface ResourceLoader {

    public Resource getResource(String location);

}
