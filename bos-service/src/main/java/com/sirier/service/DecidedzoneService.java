package com.sirier.service;

import com.sirier.domain.DecidedZone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DecidedzoneService {
    void add(DecidedZone model, String[] subareaIds);

    Page<DecidedZone> pageQuery(PageRequest pageRequest);
}
