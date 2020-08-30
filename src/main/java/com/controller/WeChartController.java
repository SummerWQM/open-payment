package com.controller;

import com.alibaba.fastjson.JSON;
import com.common.api.CommonResult;
import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.entity.PaymentTransactionResult;
import com.google.gson.annotations.JsonAdapter;
import com.services.PaymentService;
import com.services.impl.payments.IPayment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

@RestController
@RequestMapping("/pay")
public class WeChartController extends BaseController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, value = "/notify-{channel}")
    public void Notify(HttpServletRequest request, @PathVariable String channel, HttpServletResponse response) throws Exception {

        IPayment iPay = paymentService.getPay(channel);

        PaymentChannel paymentChannel = paymentService.getPaymentChannel(channel);

        BufferedReader reader = request.getReader();

        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        PaymentTransactionResult result = iPay.parsePayNotify(sb.toString(), paymentChannel);

        PaymentTransactionResult ori = paymentService.getPaymentTransactionResult(result.getPaymentTransactionUnid());

        if (ori != null && ori.getStatus().equals("success")) {
            iPay.finishNotify(response);
            return;
        }

        paymentService.createTransactionResult(result);

        PaymentTransaction paymentTransaction = paymentService.getPaymentTransaction(result.getPaymentTransactionUnid());

        paymentTransaction.setStatus(result.getStatus());
        paymentTransaction.setNotifyCount(paymentTransaction.getNotifyCount() + 1);

        paymentService.modifyTransaction(paymentTransaction);

        iPay.finishNotify(response);

        eventManager.sendMessage(JSON.toJSONString(paymentTransaction), "PAYMENT");

    }
}
