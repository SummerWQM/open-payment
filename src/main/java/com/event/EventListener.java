package com.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.config.PublisherConfig;
import com.sproutsocial.nsq.Message;
import com.sproutsocial.nsq.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


public class EventListener implements ApplicationRunner {

    private static final String CHANNEL = "payment-test-channel";

    @Autowired
    PublisherConfig publisherConfig;

    @Async
    public void run() throws Exception {
        System.out.println("********** NSQ *************");
        Subscriber subscriber = new Subscriber(publisherConfig.getNsqdLookup1(), publisherConfig.getNsqdLookup2());
        subscriber.subscribe(EventManager.PAYMENT_TOPIC, CHANNEL, EventListener::handleEvent);
        System.out.println("********** NSQ SUBSCRIBE *************");
    }


    private static void handleEvent(Message msg) {
        try {
            byte[] data = msg.getData();

            String s = new String(data, StandardCharsets.UTF_8);

            System.out.println(s);

            msg.finish();

        } catch (Exception e) {
            msg.requeue();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.run();
    }
}
