package com.sirier.web.action;

import com.sirier.domain.Role;
import com.sirier.utils.FastJsonUtils;
import com.sirier.utils.MyUtils;
import com.sirier.web.action.base.BaseAction;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/21.
 */


@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class RoleAction extends BaseAction<Role> {

    @Action(value = "roleAction_add", results = { @Result(name = "add", location =
            "/WEB-INF/pages/admin/role.jsp") })
    public String add() throws Exception {
        String menuIds = getParameter("menuIds");
        String[] functionId = getParameterValues("functionId");

        manageService.getRoleService().add(model,menuIds,functionId);
        return "add";
    }


    @Action(value = "roleAction_pageQuery")
    public String pageQuery() throws Exception {
        Page<Role> pageData = manageService.getRoleService().pageQuery
                (getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }

    //listRoleAjax
    @Action(value = "roleAction_listRoleAjax")
    public String listRoleAjax() throws Exception {
        List<Role> list = manageService.getRoleService().listRole();
        String jsonString = FastJsonUtils.toJsonWithProperty(list, "id", "name");
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }


}
