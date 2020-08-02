package com.services;

import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.services.impl.payments.IPayment;

public interface PaymentService {

    int createTransaction(PaymentTransaction paymentTransaction);

    PaymentTransaction getPaymentTransaction(String unid);

    PaymentChannel getPaymentChannel(String channelCode);

    IPayment getPay(PaymentTransaction paymentTransaction, PaymentChannel paymentChannel) throws Exception;

}
