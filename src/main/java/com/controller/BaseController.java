package com.controller;

import com.event.EventManager;
import com.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    EventManager eventManager;

    @Autowired
    RedisTemplate redisTemplate;


}
