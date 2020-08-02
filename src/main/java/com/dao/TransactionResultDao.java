package com.dao;

import com.entity.PaymentTransactionResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionResultDao {

    int insert(PaymentTransactionResult paymentTransactionResult);

    int modifyTransactionResult(PaymentTransactionResult paymentTransactionResult);

}
