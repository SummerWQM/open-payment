package com.services.impl;

import com.dao.ChannelDao;
import com.entity.Channel;
import com.services.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    protected ChannelDao channelDao;

    @Override
    public List<Channel> getChannels() {
        return channelDao.getChannels();
    }
}
