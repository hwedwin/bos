package com.sirier.web.action;

import com.sirier.domain.Region;
import com.sirier.utils.FastJsonUtils;
import com.sirier.utils.MyUtils;
import com.sirier.utils.PinYin4jUtils;
import com.sirier.web.action.base.BaseAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Sirierx on 2017/8/6.
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class RegionAction extends BaseAction<Region> {
    //接收页面上传的文件
    private File regionFile;

    public void setRegionFile(File regionFile) {
        this.regionFile = regionFile;
    }

    @Action(value = "regionAction_importXls",
            results = { @Result(name = "list",
                    location = " /WEB-INF/pages/base/region.jsp") })
    public String importXls() throws Exception {
        List<Region> list = new ArrayList<>();

        //new workbook对象
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));

        HSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                //第一行,全是标题
                continue;
            }
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();

            Region region = new Region(id, province, city, district, postcode, null, null, null);

            //pinyin4j来获取简码和城市码
            //简码就是拼音手字母大写组合而成
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
            String shortcode = StringUtils.join(headByString);

            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            region.setShortcode(shortcode);
            region.setCitycode(citycode);
            list.add(region);
        }
        manageService.getRegionService().saveAll(list);

        return "list";
    }


    @Action(value = "regionAction_pageQuery")
    public String pageQuery() throws Exception {

        Specification<Region> spec = new Specification<Region>() {
            @Override
            public Predicate toPredicate(Root<Region> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (StringUtils.isNotBlank(model.getProvince())) {
                    Predicate p1 = cb.like(root.get("province").as(String.class), "%" + model
                            .getProvince() + "%");
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(model.getCity())) {
                    Predicate p2 = cb.like(root.get("city").as(String.class), "%" + model
                            .getCity() + "%");
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(model.getDistrict())) {
                    Predicate p3 = cb.like(root.get("district").as(String.class), "%" + model
                            .getDistrict() + "%");
                    list.add(p3);
                }
                //new一个list长度的predicate数组,然后再把list转换成这个数组
                Predicate[] predicate = new Predicate[list.size()];
                return cb.and(list.toArray(predicate));
            }
        };
        //老版本的分页+条件查询
        // Page<Region> pageData = manageService.getRegionService().pageQuery(spec,
        // getPageRequest());
        // setPageData(pageData);
        //return "pageQuery";

        //新版本的分页+条件查询+redis的缓存,在存redis前fastjson序列化
        String jsonString = manageService.getRegionService().pageQuery(spec, getPageRequest());
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }


    @Action(value = "regionAction_listRegionAjax")
    public String listRegionAjax() throws Exception {
        List<Region> list = manageService.getRegionService().listRegion();
        String jsonString = FastJsonUtils.toJsonWithProperty(list, "id", "name");
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }
}
