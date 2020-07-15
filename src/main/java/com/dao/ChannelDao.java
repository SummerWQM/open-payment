package com.dao;

import com.entity.Channel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChannelDao {

    List<Channel> getChannels();

    Channel getPaymentChannel(String code);
}
