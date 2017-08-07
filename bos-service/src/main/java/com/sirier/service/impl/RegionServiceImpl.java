package com.sirier.service.impl;

import com.sirier.dao.IRegionDao;
import com.sirier.domain.Region;
import com.sirier.service.RegionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/6.
 */

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    private IRegionDao regionDao;

    @Override
    public void saveAll(List<Region> list) {
        regionDao.save(list);
    }

    @Override
    public Page<Region> pageQuery(Specification<Region> spec, PageRequest pageRequest) {
        Page<Region> pageData = regionDao.findAll(spec, pageRequest);
        return pageData;
    }
}
