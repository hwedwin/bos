package com.sirier.web.action;

import com.sirier.domain.Staff;
import com.sirier.web.action.base.BaseAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Created by Sirierx on 2017/8/5.
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class StaffAction extends BaseAction<Staff> {


    @Action(value = "staffAction_pageQuery")
    public String pageQuery() throws Exception {

        Specification<Staff> spec = new Specification<Staff>() {
            @Override//在这个内部类中封装条件进去
            public Predicate toPredicate(Root<Staff> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(model.getName())) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + model
                            .getName() + "%");
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(model.getTelephone())) {
                    Predicate p2 = cb.equal(root.get("telephone").as(String.class), model
                            .getTelephone());
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(model.getStation())) {
                    Predicate p3 = cb.like(root.get("station").as(String.class), "%" + model
                            .getStation() + "%");
                    list.add(p3);
                }
                if (StringUtils.isNotBlank(model.getStandard())) {
                    Predicate p4 = cb.equal(root.get("station").as(String.class), model
                            .getStandard());
                    list.add(p4);
                }
                Predicate[] predicates = new Predicate[list.size()];
                return cb.and(list.toArray(predicates));  //为何这样写???
            }
        };

        Page<Staff> pageData = manageService.getStaffService().pageQuery(spec,getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }


    @Action(value = "staffAction_add", results = { @Result(name = "add", location =
            "/WEB-INF/pages/base/staff.jsp") })
    public String add() throws Exception {
        manageService.getStaffService().add(model);
        return "add";
    }
}
