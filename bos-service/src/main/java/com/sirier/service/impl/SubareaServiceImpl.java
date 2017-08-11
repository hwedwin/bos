package com.sirier.service.impl;

import com.sirier.dao.ISubareaDao;
import com.sirier.domain.Subarea;
import com.sirier.service.SubareaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/7.
 */
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {

    @Autowired
    private ISubareaDao subareaDao;

    @Override
    public void add(Subarea model) {
        subareaDao.save(model);
    }

    @Override
    public Page<Subarea> pageQuery(Specification<Subarea> spec, PageRequest pageRequest) {
        Page<Subarea> pageData = subareaDao.findAll(spec,pageRequest);
        //鉴于懒加载问题,在这里手动地去查询关联的region的数据
        // for (Subarea subarea : pageData) {
        //     Hibernate.initialize(subarea.getRegion());
        // }
        //------或者选择给subarea字段加注解,立即加载
        //@ManyToOne(fetch = FetchType.EAGER)
        return pageData;
    }

    @Override
    public List<Subarea> findAllSubarea() {
        List<Subarea> list = subareaDao.findAll();
        return list;
    }

    @Override
    public List<Subarea> findSubareaByDecidedzone(String id) {
        List<Subarea> list= subareaDao.findSubareaByDecidedzone(id);
        return list;
    }
}
