package com.services.impl.payments;

import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.util.Helper;
import org.apache.tomcat.util.http.fileupload.RequestContext;
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
        params.put("nonce_str", Helper.getRandomString(32));
        params.put("body", transaction.getTransactionName());
        params.put("out_trade_no", transaction.getUnid());
        params.put("total_fee", "1");
        params.put("spbill_create_ip", "1");
        params.put("notify_url", (String) channel.getParams().get("notify_url"));
        params.put("trade_type", "NATIVE");
        params.put("product_id", "1");

        String sign = Sign(params, (String) channel.getParams().get("md5_key"));
        params.put("sign", sign);

        String st = Helper.mapToXml(params);
        params.put("st", st);
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
        signString.append("key");
        signString.append("=");
        signString.append(channelMd5Key);
        signString.deleteCharAt(signString.length() - 1);
        byte[] s = signString.toString().getBytes();
        return DigestUtils.md5DigestAsHex(s);
    }

    @Override
    public boolean refund() {
        return false;
    }

    @Override
    public String getReturnData(RequestContext requestContext) {
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

}
