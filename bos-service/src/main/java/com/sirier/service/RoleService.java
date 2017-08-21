package com.sirier.service;

import com.sirier.domain.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/21.
 */

public interface RoleService {
    void add(Role model, String menuIds, String[] functionId);

    Page<Role> pageQuery(PageRequest pageRequest);

    List<Role> listRole();

    List<Role> getRolesByUser(Integer id);
}
