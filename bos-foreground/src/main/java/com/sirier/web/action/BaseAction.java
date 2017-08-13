package com.sirier.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import org.apache.struts2.ServletActionContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Sirierx on 2017/8/2.
 */

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
        protected T model;

        @Override
        public T getModel() {
            return model;
        }

    /**
     * 获取运行期的类对象,给model实例化
     */
    public BaseAction() {
        // 对model进行实例化， 通过子类 类声明的泛型
        Type superclass = this.getClass().getGenericSuperclass();
        // 转化为参数化类型
        ParameterizedType parameterizedType = (ParameterizedType)superclass;
        // 获取一个泛型参数
        Class<T> modelClass = (Class<T>)parameterizedType.getActualTypeArguments()[0];
        try {
            model = modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取单一请求参数数值
     * @param name
     * @return
     */
    public String getParameter(String name) {
        return ServletActionContext.getRequest().getParameter(name);
    }

    /**
     * 获取多选项请求参数数值
     * @param name 往往是radio等多选项
     * @return 返回数组格式的参数值
     */
    public String[] getParameterValues(String name) {
        return ServletActionContext.getRequest().getParameterValues(name);
    }

    /**
     * 获取map格式的请求参数数值
     * @return
     */
    public Map getParameterMap() {
        return ServletActionContext.getRequest().getParameterMap();
    }


    /**
     * 通过key获取session中的value值
     * @param name
     * @return
     */
    public Object getSessionAttribute(String name) {
        return ServletActionContext.getRequest().getSession().getAttribute(name);
    }


    /**
     * 往session中存放key/value键值对
     * @param name
     * @param value
     */
    public void setSessionAttribute(String name, Object value) {
        ServletActionContext.getRequest().getSession().setAttribute(name, value);
    }


    /**
     * 移除session中存放的键值对
     * @param name
     */
    public void removeSessionAttribute(String name) {
        ServletActionContext.getRequest().getSession().removeAttribute(name);
    }

    /**
     * 往值栈中push对象
     * @param obj
     */
    // 值栈操作 后续子类actions操作
    public void push(Object obj) {
        ActionContext.getContext().getValueStack().push(obj);// root
    }

    /**
     * 往值栈中存放键值对格式的对象
     * @param key
     * @param obj
     */
    public void set(String key, Object obj) {
        ActionContext.getContext().getValueStack().set(key, obj);// root
    }


    /**
     * 往值栈中存放map对象
     * @param key
     * @param obj
     */
    public void put(String key, Object obj) {
        ActionContext.getContext().getValueStack().getContext().put(key, obj);
    }


    /**
     * 获取请求
     * @return
     */
    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

}
