package com.event;

import com.config.PublisherConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventManager {

    @Autowired
    PublisherConfig publisherConfig;

    @Autowired
    NsqPublisher nsqPublisher;

    public void sendMessage(String message, String topic) {
        nsqPublisher.getPublisher().publish(topic, message.getBytes());
    }
}
