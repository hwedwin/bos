package com.sirier.dao;

import com.sirier.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoleDao extends JpaRepository<Role,String>,
        JpaSpecificationExecutor<Role> {

    @Query("from Role r inner join fetch r.users  u  where u.id = ?1")
    List<Role> getRolesByUser(Integer id);

}
