package com.sirier.service.impl;

import com.sirier.dao.UserDao;
import com.sirier.domain.User;
import com.sirier.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sirierx on 2017/8/2.
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }
}
