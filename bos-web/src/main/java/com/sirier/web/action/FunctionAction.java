package com.sirier.web.action;

import com.sirier.domain.Function;
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
public class FunctionAction extends BaseAction<Function> {


    @Action(value = "functionAction_add", results = { @Result(name = "add", location =
            "/WEB-INF/pages/admin/function.jsp") })
    public String add() throws Exception {
        manageService.getFunctionService().add(model);
        return "add";
    }

    @Action(value = "functionAction_pageQuery")
    public String pageQuery() throws Exception {
        Page<Function> pageData = manageService.getFunctionService().pageQuery
                (getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }


    //functionAction_listFunctionAjax
    @Action(value = "functionAction_listFunctionAjax")
    public String listFunctionAjax() throws Exception {
        List<Function> list = manageService.getFunctionService().listFunction();
        String jsonString = FastJsonUtils.toJsonWithProperty(list, "id", "name");
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }

}
