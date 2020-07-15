package com.services.payments;

import com.entity.Channel;
import com.entity.Pay;
import org.apache.tomcat.util.http.fileupload.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface IPayment {

    String pay(Pay pay, Channel channel);

    boolean refund();

    //第三方支付完成，跳回到/return/{channelCode}页面时，返回要被解析的参数
    String getReturnData(RequestContext requestContext);

    //第三方支付完成，后台通知/notify/{channelCode}页面时，返回要被解析的参数，有些是GET，有些是POST
    String getNotifyData(HttpServletRequest request) throws IOException;

    //当pay完成时需要输出的内容 $result 为bool，是否成功，比如Alipay需要在正确接收后输出'success'
    String finishNotifyEcho();

    <T> Map<String, T> parseNotifyData(String notifyData);
}
