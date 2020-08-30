package com.services.impl;

import com.dao.ChannelDao;
import com.dao.TransactionDao;
import com.dao.TransactionResultDao;
import com.entity.PaymentChannel;
import com.entity.PaymentTransaction;
import com.entity.PaymentTransactionResult;
import com.services.PaymentService;
import com.services.impl.payments.IPayment;
import com.services.impl.payments.WeChatScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    protected ChannelDao channelDao;

    @Autowired

    protected TransactionDao transactionDao;

    @Autowired
    protected TransactionResultDao transactionResultDao;


    @Autowired
    protected WeChatScan weChatScan;

    public void createTransaction(PaymentTransaction paymentTransaction) {
        transactionDao.insert(paymentTransaction);
    }

    public void createTransactionResult(PaymentTransactionResult paymentTransactionResult) {
        transactionResultDao.insert(paymentTransactionResult);
    }

    @Override
    public PaymentTransactionResult getPaymentTransactionResult(String unid) {
        return transactionResultDao.getPaymentTransactionResult(unid);
    }

    public void modifyTransaction(PaymentTransaction paymentTransaction) {
        transactionDao.modifyTransaction(paymentTransaction);
    }

    @Override
    public int modifyTransactionResult(PaymentTransactionResult paymentTransactionResult) {
        return transactionResultDao.modifyTransactionResult(paymentTransactionResult);
    }

    @Override
    public PaymentTransaction getPaymentTransaction(String unid) {
        return transactionDao.getTransaction(unid);
    }

    @Override
    public PaymentChannel getPaymentChannel(String channelCode) {
        return channelDao.getPaymentChannel(channelCode);
    }


    public IPayment getPay(PaymentTransaction paymentTransaction, PaymentChannel paymentChannel) throws Exception {

        if (WeChatScan.NAME.equals(paymentChannel.getCode())) {
            return weChatScan;
        }
        throw new Exception("not found pay method");
    }

    @Override
    public IPayment getPay(String channelCode) {
        if (WeChatScan.NAME.equals(channelCode)) {
            return weChatScan;
        }
        return null;
    }

}
