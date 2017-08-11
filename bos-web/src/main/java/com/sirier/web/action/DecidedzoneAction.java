package com.sirier.web.action;

import com.sirier.domain.Customer;
import com.sirier.domain.DecidedZone;
import com.sirier.utils.FastJsonUtils;
import com.sirier.utils.LogUtils;
import com.sirier.utils.MyUtils;
import com.sirier.web.action.base.BaseAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sirierx on 2017/8/9.
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class DecidedzoneAction extends BaseAction<DecidedZone> {

    @Action(value = "decidedzoneAction_add", results = { @Result(name = "add", location =
            "/WEB-INF/pages/base/decidedzone.jsp") })
    public String add() throws Exception {
        String[] subareaIds = getParameterValues("subareaId");
        LogUtils.getInstance().warn(model);
        LogUtils.getInstance().warn(Arrays.toString(subareaIds));

        manageService.getDecidedzoneService().add(model, subareaIds);
        return "add";
    }

    //decidedzoneAction_pageQuery
    @Action(value = "decidedzoneAction_pageQuery")
    public String pageQuery() throws Exception {
        Page<DecidedZone> pageData = manageService.getDecidedzoneService().pageQuery
                (getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }


    @Action(value = "decidedzoneAction_getListHasAssociationByDecidedzone")
    public String findCustomerByDecidedzone() throws Exception {
        List<Customer> list = manageService.getCustomerService().getListHasAssociation(model
                .getId());
        LogUtils.getInstance().warn("decidedzoneAction" + list);
        String jsonString = FastJsonUtils.toJson(list);
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }

    @Action(value = "decidedzoneAction_getListNoAssociation")
    public String findNoAssignedCustomerByDecidedzone() throws Exception {
        List<Customer> list = manageService.getCustomerService().getListNotAssociation();
        String jsonString = FastJsonUtils.toJson(list);
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }

    @Action(value = "decidedzoneAction_assignedCustomerToDecidedzone",
            results = { @Result(name = "assign", location =
                    "/WEB-INF/pages/base/decidedzone.jsp") })
    public String assignedCustomerToDecidedzone() throws Exception {
        String[] customerIds = getParameterValues("customerIds");
        String customerIdsString = StringUtils.join(customerIds, ",");
        manageService.getCustomerService().assignedCustomerToDecidedzone
                (model.getId(), customerIdsString);
        return "assign";
    }


}
