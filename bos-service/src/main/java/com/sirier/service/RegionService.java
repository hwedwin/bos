package com.sirier.service;

import com.sirier.domain.Region;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RegionService {
    void saveAll(List<Region> list);

    String pageQuery(Specification<Region> spec, PageRequest pageRequest);
}
