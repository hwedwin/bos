package com.sirier.service;

import com.sirier.domain.Standard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface StandardService {
    void save(Standard model);

    Page<Standard> pageQuery(PageRequest pageRequest);

    void edit(Standard model);

    void delBatch(String ids);

    void backBatch(String ids);

    List<Standard> listStandard();

}
