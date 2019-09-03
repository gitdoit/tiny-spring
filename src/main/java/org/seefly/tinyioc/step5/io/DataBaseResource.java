package org.seefly.tinyioc.step5.io;

import java.io.InputStream;

/**
 * 描述数据库的资源
 * @author liujianxin
 * @date 2019/9/3 16:28
 */
public class DataBaseResource implements Resource {
    private String resourceId;

    public DataBaseResource(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public InputStream getInputStream() throws Exception {


        return null;
    }
}
