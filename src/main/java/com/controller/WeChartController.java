package com.controller;

import com.common.api.CommonResult;
import com.entity.PaymentChannel;
import com.entity.PaymentTransactionResult;
import com.services.PaymentService;
import com.services.impl.payments.IPayment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

@RestController
@RequestMapping("/pay")
public class WeChartController extends BaseController{

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, value = "/notify-{channel}")
    public CommonResult<Object> Notify(HttpServletRequest request, @PathVariable String channel) throws Exception {

        IPayment iPay = paymentService.getPay(channel);

        PaymentChannel paymentChannel = paymentService.getPaymentChannel(channel);

        BufferedReader reader = request.getReader();

        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        PaymentTransactionResult result =  iPay.parsePayNotify(sb.toString(),paymentChannel);



        return CommonResult.success(result);

    }
}
