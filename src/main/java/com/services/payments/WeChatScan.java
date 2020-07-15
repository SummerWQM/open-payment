package com.services.payments;

import com.entity.Channel;
import com.entity.Pay;
import org.apache.tomcat.util.http.fileupload.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class WeChatScan implements IPayment {

    public static final String NAME = "WE_CHAT_SCAN";

    @Override
    public String pay(Pay pay, Channel channel) {
        return null;
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
    public <T> Map<String, T> parseNotifyData(String notifyData) {
        return null;
    }


}
