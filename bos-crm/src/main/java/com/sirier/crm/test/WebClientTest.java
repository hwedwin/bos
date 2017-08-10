package com.sirier.crm.test;

import com.sirier.crm.domain.Customer;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by Sirierx on 2017/8/10.
 */

public class WebClientTest {

    @Test
    public void fun() {
        String url = "http://localhost:9988/crm/rs/customerService/customer";
        url = url + "/dq111";
        Collection<? extends Customer> collection = WebClient.create(url).getCollection(Customer
                .class);
       // System.out.println("customer = " + collection.toString());
    }
}
