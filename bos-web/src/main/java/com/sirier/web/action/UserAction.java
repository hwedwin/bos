package com.sirier.web.action;

import com.sirier.domain.User;
import com.sirier.web.action.base.BaseAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

/**
 * Created by Sirierx on 2017/8/2.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos")
public class UserAction extends BaseAction<User> {


    @Action(value = "userAction_login", results = {
            @Result(name = "loginSuccess", location = "/index.jsp", type = "redirect"),
            @Result(name = "loginFail", location = "/login.jsp") })
    public String login() throws Exception {
        //先验证码校验-->算了,麻烦
        String checkcode = this.getParameter("checkcode");
        //String checkcode1 = (String)this.getSessionAttribute("checkcode");
        if (StringUtils.isBlank(checkcode)) {


            // User existUser = manageService.getUserService().findByUsernameAndPassword(
            //         model.getUsername(), model.getPassword());
            // if (existUser != null) {
            //     this.setSessionAttribute("user", existUser);
            //     return "loginSuccess";
            // }
            // else {
            //     this.addActionError("用户名与密码不匹配");
            //     return "loginFail";
            // }

            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), model
                    .getPassword());
            try {
                subject.login(token);
                return "loginSuccess";
            } catch (AuthenticationException e) {
                e.printStackTrace();
                this.addActionError("用户名与密码不匹配");
                return "loginFail";
            }

        }
        else {
            // 验证失败,回去
            this.addActionError("验证码不正确");
            return "loginFail";
        }

    }

    //userAction_pageQuery

    @Action(value = "userAction_pageQuery")
    public String pageQuery() throws Exception {
        Page<User> pageData = manageService.getUserService().pageQuery
                (getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }

    //userAction_save
    @Action(value = "userAction_save", results = { @Result(name = "save", location =
            "/WEB-INF/pages/admin/userlist.jsp") })
    public String save() throws Exception {
        String[] roleIds = getParameterValues("roleIds");
        manageService.getUserService().save(model, roleIds);
        return "save";
    }

    @Action(value = "userAction_logout", results = { @Result(name = "logout", location =
            "/login.jsp") })
    public String logout() throws Exception {
        Subject sub = SecurityUtils.getSubject();
        sub.logout();
        return "logout";
    }


}
