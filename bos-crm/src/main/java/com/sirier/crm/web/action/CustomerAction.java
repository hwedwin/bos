package com.sirier.crm.web.action;

import com.sirier.crm.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Sirierx on 2017/8/10.
 */

@Controller
public class CustomerAction {

    @Autowired
    private CustomerService customerService;

}
