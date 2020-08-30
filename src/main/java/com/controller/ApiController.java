package com.controller;

import com.common.api.CommonResult;
import com.controller.vo.TransactionVo;
import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.services.impl.payments.IPayment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    @RequestMapping(method = RequestMethod.POST, value = "/transaction")
    public CommonResult<Object> createTransaction(@RequestBody TransactionVo transactionVo) {

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setUnid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        paymentTransaction.setAmount(transactionVo.getAmount());
        paymentTransaction.setStatus(PaymentTransaction.STATUS_NEW);
        paymentTransaction.setTransactionName(transactionVo.getTransactionName());
        paymentTransaction.setCreator(transactionVo.getCreator());
        paymentTransaction.setCallbackUrl(transactionVo.getCallbackUrl());
        paymentTransaction.setFromSystem(transactionVo.getFromSystem());

        paymentService.createTransaction(paymentTransaction);
        return CommonResult.success(paymentTransaction.getUnid());
    }


    @RequestMapping(method = RequestMethod.GET, value = "/pay/{unid}/{channel}")
    public CommonResult<Object> getPay(HttpServletRequest request, @PathVariable String unid, @PathVariable String channel) throws Exception {

        PaymentTransaction paymentTransaction = paymentService.getPaymentTransaction(unid);

        if (paymentTransaction == null) {
            return CommonResult.failed("未找到交易");
        }

        PaymentChannel paymentChannel = paymentService.getPaymentChannel(channel);

        if (paymentChannel == null) {
            return CommonResult.failed("不支持的交易类型");
        }

        IPayment ipay = paymentService.getPay(paymentTransaction, paymentChannel);

        Map<String, String> pay = ipay.pay(paymentTransaction, paymentChannel, request);

        return CommonResult.success(pay);

    }

}
