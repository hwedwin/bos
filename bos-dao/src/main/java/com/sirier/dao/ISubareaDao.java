package com.sirier.dao;

import com.sirier.domain.Subarea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ISubareaDao extends JpaRepository<Subarea,String>,
        JpaSpecificationExecutor<Subarea> {
}
