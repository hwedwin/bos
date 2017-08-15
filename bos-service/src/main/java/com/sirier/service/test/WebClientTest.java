package com.sirier.service.test;

import com.sirier.domain.Order;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Sirierx on 2017/8/10.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-service.xml",
        "classpath:applicationContext-domain.xml" })
public class WebClientTest {

    @Test
    public void fun() {
        String url = "http://localhost:9999/bos/rs/orderService/order/save";
        Order order = new Order();
        order.setSendName("hello");
        order.setId(1);
        WebClient.create(url).post(order);
    }
}
