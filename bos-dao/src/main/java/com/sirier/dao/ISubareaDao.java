package com.sirier.dao;

import com.sirier.domain.DecidedZone;
import com.sirier.domain.Subarea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISubareaDao extends JpaRepository<Subarea,String>,
        JpaSpecificationExecutor<Subarea> {
    @Modifying
    @Query("update Subarea  set decidedZone = ?2 where id = ?1")
        //这个地方执行更新操作,会报错,是因为uuid的原因导致model里的id值与实际存的id值不一样导致的
    void update(String subareaId, DecidedZone model);

    @Query(nativeQuery = true,value = "select * from bc_subarea where decidedzone_id = ?1")
    List<Subarea> findSubareaByDecidedzone(String id);
}
