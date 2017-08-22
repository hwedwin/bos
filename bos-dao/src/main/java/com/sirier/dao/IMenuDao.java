package com.sirier.dao;


import com.sirier.domain.Menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMenuDao extends JpaRepository<Menu,String>,
        JpaSpecificationExecutor<Menu> {
    @Query("from Menu where parentMenu = null")
    List<Menu> findTopMenu();

    @Query("from Menu where parentMenu = ?1")
    List<Menu> findAllByParentMenu(Menu parentMenu);

    @Query("from Menu m inner join fetch m.roles r inner join fetch r.users u where u.id = ?1 " +
            "order by m.zindex desc")
    List<Menu> findMenuByUser(Integer id);
}
