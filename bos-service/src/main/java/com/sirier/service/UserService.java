package com.sirier.service;

import com.sirier.domain.User;

/**
 * Created by Sirierx on 2017/8/2.
 */

public interface UserService {

    User findByUsernameAndPassword(String username, String password);
}
