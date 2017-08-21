package com.sirier.service.impl;

import com.sirier.dao.IRoleDao;
import com.sirier.domain.Function;
import com.sirier.domain.Menu;
import com.sirier.domain.Role;
import com.sirier.service.RoleService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Sirierx on 2017/8/21.
 */

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private IRoleDao roleDao;


    @Override
    public void add(Role model, String menuIds, String[] functionIds) {
        //多表操作,特别是没有实体类的中间表

        //多表对中间表的操作,是对外键实体的封装-->核心就是给实体主键id值

        roleDao.saveAndFlush(model);

        if (functionIds != null && functionIds.length != 0) {
            Set<Function> functionSet = model.getFunctions();
            for (String functionId : functionIds) {
                //然后一一保存带有id的function
                Function function = new Function(functionId);
                functionSet.add(function);
            }
        }

        if (StringUtils.isNotBlank(menuIds)) {
            Set<Menu> menuSet = model.getMenus();
            String[] ids = menuIds.split(",");
            for (String id : ids) {
                Menu menu = new Menu(id);
                menuSet.add(menu);
            }
        }

    }

    @Override
    public Page<Role> pageQuery(PageRequest pageRequest) {
        Page<Role> page = roleDao.findAll(pageRequest);
        return page;
    }

    @Override
    public List<Role> listRole() {
        List<Role> list = roleDao.findAll();
        return list;
    }

    @Override
    public List<Role> getRolesByUser(Integer id) {
        List<Role> list = roleDao.getRolesByUser(id);
        return list;
    }
}
