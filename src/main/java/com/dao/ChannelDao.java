package com.dao;

import com.entity.PaymentChannel;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ChannelDao {
    PaymentChannel getPaymentChannel(String code);
}
