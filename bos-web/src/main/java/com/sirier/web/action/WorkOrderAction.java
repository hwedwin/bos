package com.sirier.web.action;

import com.sirier.domain.WorkOrderManage;
import com.sirier.web.action.base.BaseAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

/**
 * Created by Sirierx on 2017/8/17.
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class WorkOrderAction extends BaseAction<WorkOrderManage> {


    @Action(value = "workOrderAction_save", results = { @Result(name = "save", location =
            "/WEB-INF/pages/qupai/quickworkorder.jsp") })
    public String save() throws Exception {
        manageService.getWorkOrderService().add(model);
        return "save";
    }


    @Action(value = "workOrderAction_pageQuery")
    public String pageQuery() throws Exception {
        //逻辑改变,搜索框有值,则搜索索引库,没有值则直接全加载数据库
        String conditionName = getParameter("conditionName");
        String conditionValue = getParameter("conditionValue");

        Page<WorkOrderManage> pageData = null;
        //目的一样,是获取到可分页的page数据-->list数据和total

        if (StringUtils.isBlank(conditionValue)) {
            //为空,直接查数据库
            pageData = manageService.getWorkOrderService().pageQuery
                    (getPageRequest());
        }
        else {
            //不为空,带上输入条件查索引库
            pageData =  manageService.getWorkOrderService().pageQueryByConditionSearch
                    (getPageRequest(),conditionName, conditionValue);
        }

        setPageData(pageData);
        return "pageQuery";
    }
}
