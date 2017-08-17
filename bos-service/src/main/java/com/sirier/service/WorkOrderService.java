package com.sirier.service;

import com.sirier.domain.WorkOrderManage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface WorkOrderService {
    void add(WorkOrderManage model);

    Page<WorkOrderManage> pageQuery(PageRequest pageRequest);

    //需要注意,底层索引库查询,需要的是Pageable的对象,不可以是其实现类PageRequest
    Page<WorkOrderManage> pageQueryByConditionSearch(Pageable pageRequest, String conditionName, String
            conditionValue);
}
