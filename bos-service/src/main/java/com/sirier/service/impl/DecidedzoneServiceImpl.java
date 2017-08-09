package com.sirier.service.impl;

import com.sirier.dao.IDecidedzoneDao;
import com.sirier.dao.ISubareaDao;
import com.sirier.domain.DecidedZone;
import com.sirier.service.DecidedzoneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sirierx on 2017/8/10.
 */

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {
    @Autowired
    private IDecidedzoneDao decidedzoneDao;
    @Autowired
    private ISubareaDao subareaDao;

    @Override
    public void add(DecidedZone model, String[] subareaIds) {
        decidedzoneDao.save(model);
        //保存关联的表的数据
        for (String subareaId : subareaIds) {
            //为何要把model这个父数据也传过去-->因为这是操作对象,update是整个对象
           subareaDao.update(subareaId,model);
        }

    }

    @Override
    public Page<DecidedZone> pageQuery(PageRequest pageRequest) {
        Page<DecidedZone> pageData = decidedzoneDao.findAll(pageRequest);
        return pageData;
    }
}
