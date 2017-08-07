package com.sirier.web.action;

import com.sirier.domain.Standard;
import com.sirier.domain.User;
import com.sirier.utils.FastJsonUtils;
import com.sirier.utils.LogUtils;
import com.sirier.utils.MyUtils;
import com.sirier.web.action.base.BaseAction;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

/**
 * Created by Sirierx on 2017/8/3.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class StandardAction extends BaseAction<Standard> {

    @Action(value = "standardAction_save", results = {
            @Result(name = "save", location = "/WEB-INF/pages/base/standard.jsp")
    })
    public String save() throws Exception {
        model.setOperationTime(new Date(System.currentTimeMillis()));
        User loginUser = MyUtils.getLoginUser();
        model.setOperator(loginUser.getUsername());
        model.setOperatorCompany(loginUser.getStation());

        LogUtils.getInstance().warn("----->" + model);

        manageService.getStandardService().save(model);
        return "save";
    }


    @Action(value = "standardAction_edit", results = {
            @Result(name = "edit", location = "/WEB-INF/pages/base/standard.jsp")
    })
    public String edit() throws Exception {
        LogUtils.getInstance().warn("--->" + model);
        manageService.getStandardService().edit(model);
        return "edit";
    }

    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws Exception {
        //封装请求参数
        Page<Standard> pageData = manageService.getStandardService().pageQuery(getPageRequest());
        setPageData(pageData);
        // Map<String,Object> map = new HashMap<String,Object>();
        // map.put("total", pageData.getTotalElements());
        // map.put("rows", pageData.getContent());
        //push(map);
        return "pageQuery";
    }

    @Action(value = "standardAction_delBatch", results = {
            @Result(name = "delBatch", location = "/WEB-INF/pages/base/standard.jsp")
    })
    public String delBatch() throws Exception {
        String ids = getParameter("ids");

        manageService.getStandardService().delBatch(ids);

        return "delBatch";
    }

    @Action(value = "standardAction_backBatch", results = {
            @Result(name = "backBatch", location = "/WEB-INF/pages/base/standard.jsp")
    })
    public String backBatch() throws Exception {
        String ids = getParameter("ids");
        manageService.getStandardService().backBatch(ids);
        return "backBatch";
    }

    /**
     * 查询所有的取派标准,json格式返回给staff界面
     * @return
     * @throws Exception
     */
    @Action(value = "standardAction_listStandardAjax")
    public String listStandardAjax() throws Exception {
        // List<Standard> list = manageService.getStandardService().listStandard();
        // result = list;
        // return "pageQuery";
        //把数据给到result,然后指向全局结果集即可
        List<Standard> list = manageService.getStandardService().listStandard();
        String jsonString = FastJsonUtils.toJsonWithProperty(list, "id", "name");
        // MyUtils.setJsonType();
        // MyUtils.getWriter().print(jsonString);
        MyUtils.setJsonTypeAndWriteBack(jsonString);
        return NONE;
    }


}
