package com.sirier.dao;

import com.sirier.domain.WorkOrderManage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Sirierx on 2017/8/17.
 */

public interface IWorkOrderDao extends JpaRepository<WorkOrderManage,Integer>,
        JpaSpecificationExecutor<WorkOrderManage> {


    /**
     * 按条件查询索引库,需要这个IWorkOrderDao的实现类去实现这个接口的这个方法
     * -->如何处理呢
     * -->新建一个类,起名为接口名+Impl,实现该方法即可,不要implement这个接口!!!
     * -->这属于约定编程,spring data jpa会先按照这个规则找类,找不到才会找jpa的最底层实现类
     * -->以上方法,就是spring data jpa的扩展方式,不需要implement,千万别!!!
     */
    Page<WorkOrderManage> pageQueryByConditionSearch(Pageable pageRequest, String conditionName,
                                                     String conditionValue);


}
