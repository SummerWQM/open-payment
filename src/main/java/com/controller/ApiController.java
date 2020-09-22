package com.controller;

import com.common.api.CommonResult;
import com.controller.vo.TransactionVo;
import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.services.impl.payments.IPayment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    /**
     * 申请支付交易
     *
     * @param transactionVo 申请交易信息
     * @return CommonResult
     */
    @RequestMapping(method = RequestMethod.POST, value = "/transaction")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> createTransaction(@RequestBody TransactionVo transactionVo) {

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setUnid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        paymentTransaction.setAmount(transactionVo.getAmount());
        paymentTransaction.setStatus(PaymentTransaction.STATUS_NEW);
        paymentTransaction.setTransactionName(transactionVo.getTransactionName());
        paymentTransaction.setCallbackUrl(transactionVo.getCallbackUrl());
        paymentTransaction.setFromSystem(transactionVo.getFromSystem());
        paymentService.createTransaction(paymentTransaction);

        return CommonResult.success(paymentTransaction.getUnid());
    }


    /**
     * 点击支付 获取支付模型
     *
     * @param request servletRequest
     * @param unid    交易号
     * @param channel 支付通道
     * @return CommonResult
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/pay/{unid}/{channel}")
    public CommonResult<Object> getPay(HttpServletRequest request, @PathVariable String unid, @PathVariable String channel) throws Exception {

        PaymentTransaction paymentTransaction = paymentService.getPaymentTransaction(unid);

        redisTemplate.opsForValue().setIfAbsent(unid, 0, 30, TimeUnit.SECONDS);

        if (paymentTransaction == null) {
            return CommonResult.failed("未找到交易");
        }

        PaymentChannel paymentChannel = paymentService.getPaymentChannel(channel);

        if (paymentChannel == null) {
            return CommonResult.failed("不支持的交易类型");
        }

        IPayment ipay = paymentService.getPay(paymentTransaction, paymentChannel);

        Map<String, String> pay = ipay.pay(paymentTransaction, paymentChannel, request);

        redisTemplate.delete(unid);

        return CommonResult.success(pay);

    }

    @GetMapping("/trans")
    public CommonResult<Object> T() {
        return CommonResult.success(paymentService.getPaymentTransaction("b7883e7c796e4223ab6ea3890cc844e3"));
    }
}
