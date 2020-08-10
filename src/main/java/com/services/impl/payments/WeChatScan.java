package com.services.impl.payments;

import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.util.Helper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class WeChatScan implements IPayment {

    public static final String NAME = "WECHATSCAN";

    @Override
    public Map<String, String> pay(PaymentTransaction transaction, final PaymentChannel channel) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", (String) channel.getParams().get("app_id"));
        params.put("mch_id", (String) channel.getParams().get("mch_id"));
        params.put("nonce_str", Helper.getRandomString(32));//
        params.put("body", transaction.getTransactionName());
        params.put("out_trade_no", transaction.getUnid());
        params.put("total_fee", "" + transaction.getAmount());
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("notify_url", "https://api.mch.weixin.qq.com/pay/");
        params.put("trade_type", "NATIVE");
        params.put("product_id", "1000");

        String sign = Sign(params, (String) channel.getParams().get("md5_key"));
        params.put("sign", sign);
        String st = Helper.mapToXml(params);
        params.put("st", this.requestOnce((String) channel.getParams().get("order_gateway"), st, 10000, 100000, false));
        return params;
    }


    protected String Sign(HashMap<String, String> hashMap, String channelMd5Key) {

        Set set = hashMap.keySet();
        Object[] keys = set.toArray();
        Arrays.sort(keys);

        StringBuilder signString = new StringBuilder();

        for (Object key : keys) {
            signString.append(key).append("=").append(hashMap.get(key)).append("&");
        }
        signString.append("key").append("=").append(channelMd5Key);

        byte[] s = signString.toString().getBytes();
        return DigestUtils.md5DigestAsHex(s).toUpperCase();

    }

    @Override
    public boolean refund() {
        return false;
    }

    @Override
    public String getReturnData(HttpServletRequest request) {
        return null;
    }

    @Override
    public String getNotifyData(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String str, body = "";
        while ((str = br.readLine()) != null) {
            body += str;
        }

        return "SUCCESS";
    }

    @Override
    public String finishNotifyEcho() {
        return null;
    }

    @Override
    public boolean parseNotifyData(String notifyData) {
        return false;
    }


    private String requestOnce(final String uri, String data,
                               int connectTimeoutMs, int readTimeoutMs, boolean useCert) throws Exception {
        BasicHttpClientConnectionManager connManager;

        connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );


        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();
        HttpPost httpPost = new HttpPost(uri);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", "" + 123);
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");

    }
}
