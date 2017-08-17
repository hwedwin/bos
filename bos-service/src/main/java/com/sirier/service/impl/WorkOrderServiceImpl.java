package com.sirier.service.impl;

import com.sirier.dao.IWorkOrderDao;
import com.sirier.domain.WorkOrderManage;
import com.sirier.service.WorkOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sirierx on 2017/8/17.
 */

@Service
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {
    @Autowired
    private IWorkOrderDao workOrderDao;


    @Override
    public void add(WorkOrderManage model) {
        workOrderDao.save(model);
    }

    @Override
    public Page<WorkOrderManage> pageQuery(PageRequest pageRequest) {
        Page<WorkOrderManage> pageData = workOrderDao.findAll(pageRequest);
        return pageData;
    }

    /**
     * 需要注意,底层索引库查询,需要的是Pageable的对象,不可以是其实现类PageRequest
     * @param pageRequest Pageable的对象,不可以是其实现类PageRequest
     * @param conditionName
     * @param conditionValue
     * @return
     */
    @Override
    public Page<WorkOrderManage> pageQueryByConditionSearch(Pageable pageRequest, String
            conditionName, String conditionValue) {
        Page<WorkOrderManage> pageData  =  workOrderDao.pageQueryByConditionSearch(pageRequest,
                conditionName,conditionValue);
        return pageData;
    }
}
