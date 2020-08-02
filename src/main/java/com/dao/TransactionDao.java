package com.dao;

import com.entity.PaymentTransaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionDao {

    int insert(PaymentTransaction paymentTransaction);

    PaymentTransaction getTransaction(String unid);

    int modifyTransaction(PaymentTransaction paymentTransaction);
}
