package com.sirier.crm.test;

import com.sirier.crm.domain.Customer;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/10.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-crm.xml" })
public class WebClientTest {

    @Test
    public void fun1() {
        String url = "http://localhost:9988/crm/rs/customerService/customer";
        List<Customer> list = (List<Customer>)WebClient.create(url + "/getListNotAssociation")
                .getCollection(Customer
                .class);
        System.out.println("list = " + list);
    }


    @Test
    public void fun2() {
        String url = "http://localhost:9988/crm/rs/customerService/customer";
        url = url + "/getCustomerByAddress/" + "上海";
        List<Customer> list = (List<Customer>)WebClient
                .create(url)
                .getCollection(Customer.class);
        System.out.println("list = " + list);
    }
}
