package com.services;

import com.dao.ChannelDao;
import com.entity.Channel;
import com.entity.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "Payment")
public class PaymentFactory {

    @Autowired
    private ChannelDao channelDao;


    public Pay query(Pay pay) {
        return pay;
    }


}
