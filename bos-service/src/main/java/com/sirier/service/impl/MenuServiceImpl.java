package com.sirier.service.impl;

import com.sirier.dao.IMenuDao;
import com.sirier.domain.Menu;
import com.sirier.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/19.
 */

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private IMenuDao menuDao;

    @Override
    public List<Menu> listMenu() {
        List<Menu> list = menuDao.findTopMenu();
        return list;
    }

    @Override
    public void add(Menu model) {
        menuDao.save(model);
    }

    @Override
    public Page<Menu> pageQuery(PageRequest pageRequest) {
        Page<Menu> pageData = menuDao.findAll(pageRequest);
        return pageData;
    }

    @Override
    public List<Menu> findMenuByUser(Integer id) {
        List<Menu> list = null;
        if (id == 1) { //1为超级管理员
            list = menuDao.findAll();
        }
        else {
            list = menuDao.findMenuByUser(id);
        }
        return list;
    }
}
