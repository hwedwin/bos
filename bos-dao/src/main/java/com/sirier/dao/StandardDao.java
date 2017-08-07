package com.sirier.dao;

import com.sirier.domain.Standard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StandardDao extends JpaRepository<Standard,Integer> {

    @Modifying
    @Query("update Standard set deltag = 0 where id = ?1")
    void delBatch(Integer id);

    @Modifying
    @Query("update Standard set deltag = 1 where id = ?1")
    void backBatch(Integer id);
}
