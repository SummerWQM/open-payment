package com.controller;

import com.alibaba.fastjson.JSON;
import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.entity.PaymentTransactionResult;
import com.event.EventManager;
import com.services.impl.payments.IPayment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/pay")
public class WeChartController extends BaseController {

    /**
     * 微信支付 异步通知
     *
     * @param bytes
     * @param channel
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, value = "/notify-{channel}")
    @Transactional(rollbackFor = Exception.class)
    public void Notify(@RequestBody byte[] bytes, @PathVariable String channel, HttpServletResponse response) throws Exception {

        channel = channel.toUpperCase();

        IPayment iPay = paymentService.getPay(channel);

        PaymentChannel paymentChannel = paymentService.getPaymentChannel(channel);


        PaymentTransactionResult result = iPay.parsePayNotify(new String(bytes, StandardCharsets.UTF_8), paymentChannel);

        PaymentTransactionResult ori = paymentService.getPaymentTransactionResult(result.getPaymentTransactionUnid());

        if (ori != null && ori.getStatus().equals("success")) {
            iPay.finishNotify(response);
            return;
        }

        paymentService.createTransactionResult(result);

        PaymentTransaction paymentTransaction = paymentService.getPaymentTransaction(result.getPaymentTransactionUnid());

        paymentTransaction.setStatus(result.getStatus());

        paymentService.modifyTransaction(paymentTransaction);

        iPay.finishNotify(response);

        eventManager.sendMessage(JSON.toJSONString(paymentTransaction), EventManager.PAYMENT_TOPIC);

    }


}
