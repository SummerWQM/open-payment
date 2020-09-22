package com.dao;


import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.PaymentTransaction;
import org.apache.ibatis.annotations.Mapper;

public interface TransactionDao extends IService<PaymentTransaction> {

    PaymentTransaction getTransaction(String unid);

    int modifyTransaction(PaymentTransaction paymentTransaction);


}
