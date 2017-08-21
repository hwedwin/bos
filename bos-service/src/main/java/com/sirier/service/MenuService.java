package com.sirier.service;

import com.sirier.domain.Menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MenuService {
    List<Menu> listMenu();

    void add(Menu model);

    Page<Menu> pageQuery(PageRequest pageRequest);

}
