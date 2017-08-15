package com.sirier.crm.service.impl;

import com.sirier.crm.dao.CustomerDao;
import com.sirier.crm.domain.Customer;
import com.sirier.crm.service.CustomerService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/10.
 */

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    public List<Customer> getListNotAssociation() {
        return customerDao.getListNotAssociation();
    }

    public List<Customer> getListHasAssociation(String decidezoneId) {
        return customerDao.getListHasAssociation(decidezoneId);
    }

    public void assignedCustomerToDecidedzone(String decidezoneId, String customerIds) {
        //先取消当前定区管理的所有
        customerDao.cancleCustomerToDecidedzone(decidezoneId);
        //再一一绑定需要绑定的
        if (StringUtils.isNoneBlank(customerIds)) {
            String cIds[] = customerIds.split(",");
            for (String id : cIds) {
                System.out.println("id = " + id);
                customerDao.assignedCustomerToDecidedzone(decidezoneId, Integer.parseInt(id));
            }
        }
    }

    @Override
    public List<Customer> getCustomerByAddress(String address) {
        List<Customer> list = customerDao.getCustomerByAddress(address);
        return list;
    }

}
