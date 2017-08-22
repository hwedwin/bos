package com.sirier.service.impl;

import com.sirier.dao.StandardDao;
import com.sirier.domain.Standard;
import com.sirier.service.StandardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/3.
 */

@Service
@Transactional
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardDao standardDao;

    @Override
    @CacheEvict(allEntries = true,value = "staff")
    public void save(Standard model) {
        standardDao.save(model);
    }

    /**
     * 鉴于主键唯一,在这里不去写update的sql,直接save去覆盖得了
     * @param model 需要更新的实体类
     */
    @Override
    public void edit(Standard model) {
        standardDao.save(model);
    }

    @Override
    public void delBatch(String ids) {
        for (String id : ids.split(",")) {
            standardDao.delBatch(Integer.valueOf(id));
        }

    }

    @Override
    public void backBatch(String ids) {
        for (String id : ids.split(",")) {
            standardDao.backBatch(Integer.valueOf(id));
        }
    }

    @Override
    @Cacheable(value = "staff")
    public List<Standard> listStandard() {
        List<Standard> list = standardDao.findAll();
        return list;
    }

    @Override
    @Cacheable(value = "staff", key = "#pageRequest.pageNumber+'_'+#pageRequest.pageSize")
    public Page<Standard> pageQuery(PageRequest pageRequest) {
        //开始使用spring data的自带方法
        Page<Standard> pageData = standardDao.findAll(pageRequest);
        return pageData;
    }


}
