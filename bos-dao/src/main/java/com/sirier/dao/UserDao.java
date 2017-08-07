package com.sirier.dao;

import com.sirier.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User的dao接口,实现jpa接口
 * Created by Sirierx on 2017/8/2.
 */

public interface UserDao extends JpaRepository<User,Integer> {

    User findByUsernameAndPassword(String username, String password);

}
