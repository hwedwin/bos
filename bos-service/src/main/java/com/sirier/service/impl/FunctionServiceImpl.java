package com.sirier.service.impl;

import com.sirier.dao.IFunctionDao;
import com.sirier.domain.Function;
import com.sirier.service.FunctionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/21.
 */

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private IFunctionDao functionDao;


    @Override
    public void add(Function model) {
        functionDao.save(model);
    }

    @Override
    public Page<Function> pageQuery(PageRequest pageRequest) {
        Page<Function> page = functionDao.findAll(pageRequest);
        return page;
    }

    @Override
    public List<Function> listFunction() {
        List<Function> list = functionDao.findAll();
        return list;
    }
}
