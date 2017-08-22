package com.sirier.web.action;

import com.sirier.domain.Menu;
import com.sirier.domain.User;
import com.sirier.utils.FastJsonUtils;
import com.sirier.utils.MyUtils;
import com.sirier.web.action.base.BaseAction;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/19.
 */


@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class MenuAction extends BaseAction<Menu> {

    @Action(value = "menuAction_add", results = { @Result(name = "add", location =
            "/WEB-INF/pages/admin/menu.jsp") })
    public String add() throws Exception {
        manageService.getMenuService().add(model);
        return "add";
    }

    @Action(value = "menuAction_listMenuAjax",results = {@Result(name = "list",type = "json")})
    public String listMenuAjax() throws Exception {
        List<Menu> list = manageService.getMenuService().listMenu();
        // String jsonString = JSON.toJSONString(list);
        // MyUtils.setJsonTypeAndWriteBack(jsonString);
        push(list);
        return "list";
    }


    @Action(value = "menuAction_pageQuery")
    public String pageQuery() throws Exception {
        setPage(Integer.parseInt(model.getPage()));
        Page<Menu> pageData = manageService.getMenuService().pageQuery
                (getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }


    @Action(value = "menuAction_findMenuByUser")
    public String findMenuByUser() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        List<Menu> list = manageService.getMenuService().findMenuByUser(user.getId());
        String jsonString = FastJsonUtils.toJsonWithProperty(list, "id", "name","pId","page");
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }

}
