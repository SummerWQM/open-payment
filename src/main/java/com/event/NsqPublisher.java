package com.event;

import com.config.PublisherConfig;
import com.sproutsocial.nsq.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class NsqPublisher {

    @Autowired
    PublisherConfig publisherConfig;

    private Publisher publisher;


    public Publisher getPublisher() {
        if (publisher != null) {
            return publisher;
        }
        return this.publisher = new Publisher(publisherConfig.getNsqdTcp());
    }
}
