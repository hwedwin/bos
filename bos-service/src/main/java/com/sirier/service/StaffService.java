package com.sirier.service;

import com.sirier.domain.Staff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface StaffService {
    Page<Staff> pageQuery(Specification<Staff> spec, PageRequest pageRequest);

    void add(Staff model);
}
