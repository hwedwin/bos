package com.sirier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * service的统一管理,方便action获取到所有的service对象
 * Created by Sirierx on 2017/8/2.
 */

@Service
public class ManageService {
    @Autowired
    private UserService userService;
    @Autowired
    private StandardService standardService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private SubareaService subareaService;


    @Autowired
    private DecidedzoneService decidedzoneService;

    public SubareaService getSubareaService() {
        return subareaService;
    }

    public RegionService getRegionService() {
        return regionService;
    }

    public StaffService getStaffService() {
        return staffService;
    }

    public UserService getUserService() {
        return userService;
    }

    public StandardService getStandardService() {
        return standardService;
    }

    public DecidedzoneService getDecidedzoneService() {
        return decidedzoneService;
    }

}
