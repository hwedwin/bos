package com.sirier.dao;

import com.sirier.domain.Function;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFunctionDao extends JpaRepository<Function,String>,
        JpaSpecificationExecutor<Function> {
}
