package com.sirier.dao;

import com.sirier.domain.Region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IRegionDao extends JpaRepository<Region,String>, JpaSpecificationExecutor<Region> {


    /**
     * 通过省市区定位到区域对象
     * @param province
     * @param city
     * @param district
     * @return
     */
    @Query("from Region where province = ?1 and city = ?2 and district = ?3")
    Region findRegionByProvinceAndCityAndDistinct(String province, String city, String district);

}
