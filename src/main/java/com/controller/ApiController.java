package com.controller;

import com.common.api.CommonResult;
import com.entity.Channel;
import com.job.HttpSenderRunnable;
import com.services.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
public class ApiController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/value")
    public CommonResult<Object> getPayUrl() {

        List<Channel> re = channelService.getChannels();

        return CommonResult.success(re);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api")
    public CommonResult<Object> result(HttpServletRequest request) {

        return CommonResult.success(request.getRemoteAddr());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/notify/{channel:[a-z0-9_]+}")
    public String Notify(@PathVariable String channel) {

        return channel;
    }

    public void run() throws URISyntaxException, InterruptedException {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        HashMap<Integer, Thread> map = new HashMap<>();
        final CountDownLatch cdl = new CountDownLatch(1000);
        for (int i = 0; i < 1500; i++) {


            Thread sender = new Thread(new HttpSenderRunnable());
            sender.setName("线程:" + i);
            map.put(i, sender);
        }


        for (Object key : map.keySet()) {
            Thread thread = map.get(key);
            thread.start();

        }

        cdl.await();

    }

}
