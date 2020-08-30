package com.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class PublisherConfig {

    @Value("${nsq.nsqd-http}")
    private String nsqdHttp;

    @Value("${nsq.nsqd-tcp}")
    private String nsqdTcp;

    @Value("${nsq.nsqd-lookup1}")
    private String nsqdLookup1;


    @Value("${nsq.nsqd-lookup1}")
    private String nsqdLookup2;

}
