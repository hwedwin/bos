package com.sirier.dao;

import com.sirier.domain.DecidedZone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IDecidedzoneDao extends JpaRepository<DecidedZone,String>,
        JpaSpecificationExecutor<DecidedZone> {
}
