package com.sirier.service;

import com.sirier.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by Sirierx on 2017/8/2.
 */

public interface UserService {

    User findByUsernameAndPassword(String username, String password);

    Page<User> pageQuery(PageRequest pageRequest);

    void save(User model, String[] roleIds);
}
