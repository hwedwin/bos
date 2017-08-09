package com.sirier.service.impl;

import com.sirier.dao.RegionDao;
import com.sirier.domain.Region;
import com.sirier.service.RegionService;
import com.sirier.utils.FastJsonUtils;
import com.sirier.utils.LogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sirierx on 2017/8/6.
 */

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveAll(List<Region> list) {
        regionDao.save(list);
    }

    public String pageQuery(Specification<Region> spec, PageRequest pageRequest) {

        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        String className = this.getClass().getSimpleName();
        String key = className + "_" + pageNumber + "_" + pageSize;

        //要考虑redis服务器开关问题
        String jsonString = (String)redisTemplate.opsForValue().get(key);

        if (StringUtils.isBlank(jsonString)) {
            //如果没数据,则查询,然后放置到redis中去
            Page<Region> pageData = regionDao.findAll(spec, pageRequest);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("total", pageData.getTotalElements());
            map.put("rows", pageData.getContent());
            //json序列化
            jsonString = FastJsonUtils.toJson(map);
            //存数据库
            redisTemplate.opsForValue().set(key, jsonString);
        }
        else {
            LogUtils.getInstance().warn("从redis获取缓存");
        }
        return jsonString;
    }

    @Override
    public List<Region> listRegion() {
        List<Region> list = regionDao.findAll();
        return list;
    }
}
