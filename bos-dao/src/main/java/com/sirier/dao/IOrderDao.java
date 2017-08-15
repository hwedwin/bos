package com.sirier.dao;

import com.sirier.domain.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IOrderDao extends JpaRepository<Order,Integer>, JpaSpecificationExecutor<Order> {
}
