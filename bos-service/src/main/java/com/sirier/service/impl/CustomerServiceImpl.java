package com.sirier.service.impl;

import com.sirier.domain.Customer;
import com.sirier.service.CustomerService;
import com.sirier.utils.LogUtils;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/11.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static String url = "http://localhost:9988/crm/rs/customerService/customer";

    @Override
    public List<Customer> getListNotAssociation() {
        List<Customer> list = (List<Customer>)WebClient.create(url).getCollection(Customer
                .class);
        LogUtils.getInstance().warn("customerServiceImpl+------->"+list);
        return list;
    }

    @Override
    public List<Customer> getListHasAssociation(String decidezoneId) {
        List<Customer> list = (List<Customer>)WebClient.create(url+"/" + decidezoneId).getCollection(Customer
                .class);
        return list;
    }

    @Override
    public void assignedCustomerToDecidedzone(String decidezoneId, String customerIds) {
        //因为没有实体,所以直接put(null)
        WebClient.create(url+"/" + decidezoneId + "/" + customerIds).put(null);
    }
}
