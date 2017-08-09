package com.sirier.web.action;

import com.sirier.domain.Region;
import com.sirier.domain.Subarea;
import com.sirier.utils.FastJsonUtils;
import com.sirier.utils.LogUtils;
import com.sirier.utils.MyUtils;
import com.sirier.web.action.base.BaseAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Sirierx on 2017/8/7.
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class SubareaAction extends BaseAction<Subarea> {

    @Action(value = "subareaAction_add",
            results = { @Result(name = "add",
                    location = "/WEB-INF/pages/base/subarea.jsp") })
    public String add() throws Exception {
        LogUtils.getInstance().warn(model.getAddresskey());
        manageService.getSubareaService().add(model);
        return "add";
    }


    @Action(value = "subareaAction_pageQuery")
    public String pageQuery() throws Exception {
        LogUtils.getInstance().warn(model);

        Specification<Subarea> spec = new Specification<Subarea>() {
            @Override//在这个内部类中封装条件进去
            public Predicate toPredicate(Root<Subarea> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                // if (model.getRegion() != null) {
                //     Predicate p1 = cb.like(root.get("province").as(String.class), "%" + model
                //             .getRegion().getProvince() + "%");
                //     list.add(p1);
                // }
                // if (model.getRegion() != null) {
                //     Predicate p2 = cb.like(root.get("city").as(String.class), "%" + model
                //             .getRegion().getCity() + "%");
                //     list.add(p2);
                // }
                // if (model.getRegion() != null) {
                //     Predicate p3 = cb.like(root.get("district").as(String.class), "%" + model
                //             .getRegion().getDistrict() + "%");
                //     list.add(p3);
                // }
                // if (model.getDecidedZone() != null) {
                //     Predicate p4 = cb.like(root.get("decidedzone.id").as(String.class), "%" +
                // model
                //             .getDecidedZone().getId() + "%");
                //     list.add(p4);
                // }
                // if (StringUtils.isNotBlank(model.getAddresskey())) {
                //     Predicate p5 = cb.equal(root.get("addresskey").as(String.class), model
                //             .getAddresskey());
                //     list.add(p5);
                // }

                //-----------------------------------------------------------------------------
                //-----------如上的是自写的非关联多表条件查询,如下是使用api-join做的多表条件关联查询----
                //-----------------------------------------------------------------------------

                //本表字段
                if (StringUtils.isNotBlank(model.getAddresskey())) {
                    Predicate p1 = cb.equal(root.get("addresskey").as(String.class), model
                            .getAddresskey());
                    list.add(p1);
                }

                //关联表字段
                if (model.getDecidedZone() != null &&
                        //因为后面要用到这个id,所以要非空判定
                        StringUtils.isNotBlank(model.getDecidedZone().getId())) {
                    Predicate p2 = cb.like(root.get("decidedzone.id").as(String.class), "%" +
                            model.getDecidedZone().getId() + "%");
                    list.add(p2);
                }

                //关联表字段+关联条件
                if (model.getRegion() != null) {
                    //多方n关联一方1,主表关联从表
                    //---->下面这个方法算是模版代码,可以看做是给表关联并且取了别名(没用到)
                    //--->关联后,get("xxx")不再是root,而是rootJoin
                    Join<Subarea,Region> regionJoin = root.join(
                            root.getModel().getSingularAttribute("region", Region.class),
                            JoinType.LEFT);

                    if (StringUtils.isNotBlank(model.getRegion().getProvince())) {
                        Predicate p3 = cb.like(regionJoin.get("province").as(String.class), "%"
                                + model.getRegion().getProvince() + "%");
                        list.add(p3);
                    }

                    if (model.getRegion() != null) {
                        Predicate p4 = cb.like(regionJoin.get("city").as(String.class), "%" + model
                                .getRegion().getCity() + "%");
                        list.add(p4);
                    }
                    if (model.getRegion() != null) {
                        Predicate p5 = cb.like(regionJoin.get("district").as(String.class), "%"
                                + model.getRegion().getDistrict() + "%");
                        list.add(p5);
                    }
                }
                Predicate[] predicates = new Predicate[list.size()];
                return cb.and(list.toArray(predicates));
            }
        };

        Page<Subarea> pageData = manageService.getSubareaService().pageQuery(spec,
                getPageRequest
                        ());

        setPageData(pageData);
        return "pageQuery";
    }


    @Action(value = "subareaAction_exportXls")
    public String exportXls() throws Exception {
        //先查询到数据
        List<Subarea> list = manageService.getSubareaService().findAllSubarea();
        LogUtils.getInstance().warn(list.size());

        //然后开始拼接sheet
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("分区数据");

        for (Subarea subarea : list) {
            //第一行,是标题等等数据

            HSSFRow headerRow = sheet.createRow(0);// 即为标题行
            headerRow.createCell(0).setCellValue("分区编号");
            headerRow.createCell(1).setCellValue("开始编号");
            headerRow.createCell(2).setCellValue("结束编号");
            headerRow.createCell(3).setCellValue("位置信息");
            headerRow.createCell(4).setCellValue("省市区");

            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);//获取最后一行的rowNum

            row.createCell(0).setCellValue(subarea.getId());
            row.createCell(1).setCellValue(subarea.getStartnum());
            row.createCell(2).setCellValue(subarea.getEndnum());
            row.createCell(3).setCellValue(subarea.getPosition());
            row.createCell(4).setCellValue(subarea.getRegion().getName());//->必须要立即查询
        }

        String filename = "分区数据.xls";
        downloadSheet(filename, workbook);

        return NONE;
    }

    //subareaAction_listSubareaAjax

    @Action(value = "subareaAction_listSubareaAjax")
    public String listSubareaAjax() throws Exception {
        //把数据给到result,然后指向全局结果集即可
        List<Subarea> list = manageService.getSubareaService().findAllSubarea();
        // String jsonString = FastJsonUtils.toJsonWithProperty(list, "id", "addresskey","position");
        // String jsonString = FastJsonUtils.toJsonWithProperty(list, "subareaId", "addresskey","position");

        String jsonString = FastJsonUtils.toJson(list);
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }

}
