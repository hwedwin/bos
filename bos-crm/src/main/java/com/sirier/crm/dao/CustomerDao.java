package com.sirier.crm.dao;

import com.sirier.crm.domain.Customer;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/10.
 */

public interface CustomerDao {

    // 获取定区未关联的客户信息
    public List<Customer> getListNotAssociation();

    // 获取指定定区绑定客户信息
    public List<Customer> getListHasAssociation(String decidezoneId);

    // 定区绑定客户
    public void assignedCustomerToDecidedzone(String decidedzoneId, Integer customerId);

    // 取消定区关联所有客户
    public void cancleCustomerToDecidedzone(String decidedzoneId);

    //通过address获取customer对象
    List<Customer> getCustomerByAddress(String address);
}
