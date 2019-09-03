package org.seefly.tinyioc.step4.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 描述了一个Url资源，即可以从Url中获取资源
 * @author liujianxin
 * @date 2019/9/3 10:57
 */
public class UrlResource implements Resource {
    private final URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }
}
