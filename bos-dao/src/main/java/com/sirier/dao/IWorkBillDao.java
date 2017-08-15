package com.sirier.dao;

import com.sirier.domain.WorkBill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IWorkBillDao extends JpaRepository<WorkBill,Integer>,
        JpaSpecificationExecutor<WorkBill> {
}
