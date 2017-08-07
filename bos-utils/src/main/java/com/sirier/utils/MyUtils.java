package com.sirier.utils;

import com.sirier.domain.User;

import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

public class MyUtils {
    public static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    public static User getLoginUser() {
        return (User)getSession().getAttribute("user");
    }

    public static Object getSessionAttr(String key) {
        return getSession().getAttribute(key);
    }

    public static void setSessionAttr(String key, Object obj) {
        getSession().setAttribute(key, obj);
    }

    public static PrintWriter getWriter() {
        try {
            return ServletActionContext.getResponse().getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setJsonType() {
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
    }

    public static void setJsonTypeAndWriteBack(String jsonString) {
        try {
            ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
            ServletActionContext.getResponse().getWriter().write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
