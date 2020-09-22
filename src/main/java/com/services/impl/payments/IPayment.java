package com.services.impl.payments;

import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.entity.PaymentTransactionResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IPayment {

    Map<String, String> pay(PaymentTransaction pay, PaymentChannel channel, HttpServletRequest request) throws Exception;

    PaymentTransactionResult parsePayNotify(final Object object, PaymentChannel channel) throws Exception;

    void finishNotify(HttpServletResponse response) throws Exception;
}
