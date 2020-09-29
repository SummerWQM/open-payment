package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.PaymentTransactionResult;

public interface TransactionResultDao extends BaseMapper<PaymentTransactionResult> {

    int insert(PaymentTransactionResult paymentTransactionResult);

    int modifyTransactionResult(PaymentTransactionResult paymentTransactionResult);

    PaymentTransactionResult getPaymentTransactionResult(String unid);
}
