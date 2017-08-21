package com.sirier.service;

import com.sirier.domain.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface FunctionService {
    void add(Function model);

    Page<Function> pageQuery(PageRequest pageRequest);

    List<Function> listFunction();
}
