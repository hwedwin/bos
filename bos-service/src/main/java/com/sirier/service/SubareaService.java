package com.sirier.service;

import com.sirier.domain.Subarea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SubareaService {
    void add(Subarea model);

    Page<Subarea> pageQuery(Specification<Subarea> spec, PageRequest pageRequest);

    List<Subarea> findAllSubarea();

    List<Subarea> findSubareaByDecidedzone(String id);
}
