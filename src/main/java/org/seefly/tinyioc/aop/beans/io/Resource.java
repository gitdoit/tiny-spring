package org.seefly.tinyioc.aop.beans.io;

import java.io.InputStream;

/**
 * Resource是Spring内部的资源定位接口，它用于描述一个资源
 * @author liujianxin
 */
public interface Resource {

    InputStream getInputStream() throws Exception;
}
