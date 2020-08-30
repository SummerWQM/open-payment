package com.services.impl.payments;

import com.alibaba.fastjson.JSON;
import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.entity.PaymentTransactionResult;
import com.util.Helper;
import com.util.HttpSender;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class WeChatScan implements IPayment {


    public static final String NAME = "WECHATSCAN";

    private static final String TYPE = "QR";

    @Override
    public Map<String, String> pay(PaymentTransaction transaction, final PaymentChannel channel, HttpServletRequest request) throws Exception {

        HashMap<String, String> requestMap = new HashMap<>();

        requestMap.put("appid", (String) channel.getParams().get("app_id"));
        requestMap.put("mch_id", (String) channel.getParams().get("mch_id"));
        requestMap.put("nonce_str", Helper.getRandomString(32));
        requestMap.put("body", transaction.getTransactionName());
        requestMap.put("out_trade_no", transaction.getUnid());
        requestMap.put("total_fee", "" + transaction.getAmount());
        requestMap.put("spbill_create_ip", request.getRemoteAddr());
        requestMap.put("notify_url", (String) channel.getParams().get("notify_url"));
        requestMap.put("trade_type", "NATIVE");

        requestMap.put("sign", sign(requestMap, (String) channel.getParams().get("md5_key")));

        String xml = Helper.mapToXml(requestMap);

        String result = HttpSender.requestPost((String) channel.getParams().get("order_gateway"), xml);

        Map<String, String> re = Helper.xmlToMap(result);

        return parseApplyPay(re);
    }

    protected Map<String, String> parseApplyPay(Map<String, String> re) {
        Map<String, String> map = new HashMap<>();
        String code = re.get("result_code");
        if (code.equals("SUCCESS")) {
            map.put("code", code);
            map.put("TYPE", TYPE);
            map.put("uri", re.get("code_url"));
            return map;
        }
        map.put("code", re.get("result_code"));

        return map;

    }


    @Override
    public PaymentTransactionResult parsePayNotify(final Object object, PaymentChannel channel) throws Exception {

        final String message = (String) object;
        PaymentTransactionResult paymentTransactionResult = new PaymentTransactionResult();
        paymentTransactionResult.setMessageInfo(message);

        Map<String, String> re = Helper.xmlToMap(message);
        paymentTransactionResult.setPaymentTransactionUnid(re.get("out_trade_no"));

        paymentTransactionResult.setAmount(Integer.parseInt(re.get("total_fee")));

        paymentTransactionResult.setDiscountAmount(Integer.parseInt(re.get("coupon_fee")));

        String code = re.get("return_code");

        if (code.equals("SUCCESS")) {
            paymentTransactionResult.setStatus("success");
        } else {
            paymentTransactionResult.setStatus("failed");
        }

        // 验证签名
        String v_sign = sign(re, (String) channel.getParams().get("order_gateway"));


        return paymentTransactionResult;
    }


    protected String sign(Map<String, String> hashMap, String md5Key) {

        Set set = hashMap.keySet();

        Object[] keys = set.toArray();

        Arrays.sort(keys);

        StringBuilder stringBuilder = new StringBuilder();
        for (Object key : keys) {
            String value = hashMap.get(key);
            value = value.trim();
            if (value.length() == 0) {
                continue;
            }
            stringBuilder.append(key).append("=").append(value).append("&");
        }

        stringBuilder.append("key").append("=").append(md5Key);

        byte[] s = stringBuilder.toString().getBytes();
        return DigestUtils.md5DigestAsHex(s).toUpperCase();
    }
}
