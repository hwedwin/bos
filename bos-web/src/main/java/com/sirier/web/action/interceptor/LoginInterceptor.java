package com.sirier.web.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.sirier.domain.User;
import com.sirier.utils.LogUtils;

import org.apache.struts2.ServletActionContext;

public class LoginInterceptor extends MethodFilterInterceptor {

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        // -->前置拦截代码
        User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
        if (user == null) {
            // 动态添加错误信息
            ActionSupport action = (ActionSupport)invocation.getAction();
            action.addFieldError("userError", "请先登录");
            LogUtils.getInstance().warn("登录拦截了");
            return "no_login";
        }
        else {
            String invoke = invocation.invoke();
            // -->后置拦截代码
            return invoke;
        }

    }

}
