package com.sirier.dao;

import com.sirier.domain.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StaffDao extends JpaRepository<Staff,String>,JpaSpecificationExecutor<Staff> {

}
