package com.job;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;


public class HttpSenderRunnable implements Runnable {
    //
    //
    //protected String uri = "http://39.108.181.207";

    //protected String uri = "https://www.shixiseng.com/app/interns/search/v2?build_time=1593835102048&page=1&keyword=%E6%8A%95%E8%B5%84&type=intern&area=&months=3&days=&degree=%E7%A1%95%E5%A3%AB&official=&enterprise=&salary=-0&publishTime=&sortType=&city=%E7%BB%B5%E9%98%B3&internExtend=";
    public static final int cache = 10 * 1024;
    protected String uri = "https://www.shixiseng.com/app/interns/search/v2?page=21&keyword=23&type=intern&area=&months=3&days=&degree=&official=&enterprise=&salary=-0&publishTime=&sortType=&city=%E5%85%A8%E5%9B%BD&internExtend=";
    protected HttpGet httpGet;

    private CloseableHttpClient closeableHttpClient;

    @Override
    public void run() {

        while (true) {
            try {
                this.sender();

            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HttpSenderRunnable() throws URISyntaxException {
        this.httpGet = new HttpGet(new URI(this.uri));
        httpGet.setHeader("user-agent", "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.4; InfoPath.2; SV1; .NET CLR 3.3.69573; WOW64; en-US)");

        RequestConfig requestConfig = RequestConfig.custom()
                //
                //
                // .setProxy(new HttpHost("124.112.236.121", 23680))
                .setConnectTimeout(500).build();

        this.closeableHttpClient = HttpClients.createDefault();
        this.httpGet.setConfig(requestConfig);
    }


    protected void sender() throws URISyntaxException, IOException {
        CloseableHttpResponse Response = this.closeableHttpClient.execute(httpGet);
        System.out.println(Response.getProtocolVersion());
        System.out.println(Response.getStatusLine());//打印捕获的返回状态

        System.out.println(Response.getStatusLine().getStatusCode());    //打印捕获的状态码
        HttpEntity entity = Response.getEntity();
        InputStream is = entity.getContent();

        String filepath = "/tmp/";

        File file = new File(filepath);
        file.getParentFile().mkdirs();

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        byte[] buffer = new byte[cache];
        int ch = 0;
        while ((ch = is.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, ch);
        }

        is.close();
        fileOutputStream.flush();
        fileOutputStream.close();

        Response.close();
    }
}
