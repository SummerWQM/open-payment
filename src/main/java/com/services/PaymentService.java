package com.services;

import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.entity.PaymentTransactionResult;
import com.services.impl.payments.IPayment;

public interface PaymentService {

    void createTransaction(PaymentTransaction paymentTransaction);

    PaymentTransaction getPaymentTransaction(String unid);

    PaymentChannel getPaymentChannel(String channelCode);

    IPayment getPay(PaymentTransaction paymentTransaction, PaymentChannel paymentChannel) throws Exception;

    IPayment getPay(String channelCode);

    void createTransactionResult(PaymentTransactionResult paymentTransactionResult);

    PaymentTransactionResult getPaymentTransactionResult(String unid);


    void modifyTransaction(PaymentTransaction paymentTransaction);

    int modifyTransactionResult(PaymentTransactionResult paymentTransactionResult);
}
