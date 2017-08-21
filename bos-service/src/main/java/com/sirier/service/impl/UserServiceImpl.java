package com.sirier.service.impl;

import com.sirier.dao.UserDao;
import com.sirier.domain.Role;
import com.sirier.domain.User;
import com.sirier.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
        return userDao.findByUsernameAndPassword(username, password);
    }

    @Override
    public Page<User> pageQuery(PageRequest pageRequest) {
        Page<User> page = userDao.findAll(pageRequest);
        return page;
    }

    @Override
    public void save(User model, String[] roleIds) {
        userDao.saveAndFlush(model);
        Set<Role> roles = model.getRoles();
        if (roleIds != null && roleIds.length > 0) {
            for (String roleId : roleIds) {
                Role role = new Role(roleId);
                roles.add(role);
            }
        }
    }
}
