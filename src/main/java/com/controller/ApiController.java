package com.controller;

import com.common.api.CommonResult;
import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.services.PaymentService;
import com.services.impl.payments.IPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET, value = "/set")
    public CommonResult<Object> createTransaction() {

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setUnid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        paymentTransaction.setAmount(100);
        paymentTransaction.setStatus(PaymentTransaction.STATUS_NEW);
        paymentTransaction.setTransactionName("测试交易数据");
        paymentTransaction.setCreator(1);
        paymentTransaction.setCallbackUrl("http://example.com");
        paymentTransaction.setFromSystem("ORDER_SYSTEM");
        int count = paymentService.createTransaction(paymentTransaction);

        return CommonResult.success(count);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/pay/{unid}/{channel}")
    public CommonResult<Object> getPay(HttpServletResponse response, @PathVariable String unid, @PathVariable String channel) throws Exception {

        PaymentTransaction paymentTransaction = paymentService.getPaymentTransaction(unid);

        if (paymentTransaction == null) {
            return CommonResult.failed("未找到交易");
        }
        PaymentChannel paymentChannel = paymentService.getPaymentChannel(channel);

        if (paymentChannel == null) {
            return CommonResult.failed("不支持的交易类型");
        }

        IPayment ipay = paymentService.getPay(paymentTransaction, paymentChannel);

        Map<String, String> pay = ipay.pay(paymentTransaction, paymentChannel);

        return CommonResult.success(pay);

    }


}
