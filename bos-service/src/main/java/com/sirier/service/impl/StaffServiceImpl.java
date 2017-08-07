package com.sirier.service.impl;

import com.sirier.dao.StaffDao;
import com.sirier.domain.Staff;
import com.sirier.service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sirierx on 2017/8/5.
 */

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDao staffDao;


    @Override
    public Page<Staff> pageQuery(Specification<Staff> spec, PageRequest pageRequest) {
        Page<Staff> pageData = staffDao.findAll(spec, pageRequest);
        return pageData;
    }

    @Override
    public void add(Staff model) {
        staffDao.save(model);
    }
}
