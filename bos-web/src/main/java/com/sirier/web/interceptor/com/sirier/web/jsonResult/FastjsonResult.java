package com.sirier.web.interceptor.com.sirier.web.jsonResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;

import org.apache.struts2.StrutsStatics;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

/**
 * 自定义fastjson序列化结果集来替换struts的默认json插件
 * Created by Sirierx on 2017/8/7.
 */

public class FastjsonResult implements Result {
    //参照JsonResult.java这个文件去写

    /**
     * root作为一个标志,以是否有值判定是序列该值对应的数据还是序列化栈顶
     */
    private String root;

    /**
     * 不需要序列化的字段
     */
    protected Set<String> excludeProperties = Collections.emptySet();

    /**
     * 指定序列化的字段
     */
    protected Set<String> includeProperties = Collections.emptySet();

    public Set<String> getExcludeProperties() {
        return excludeProperties;
    }

    /**
     * @param excludeProperties
     */
    public void setExcludeProperties(String excludeProperties) {
        //该TextParseUtil 由struts2 框架提供 用于切割 逗号分隔的字符串 该方法来源 struts2 自带的拦截器源码  MethodFilterInterceptor
        //和常规的切割不一样,还是使用框架的比较好-->作用是把带","的字符串转换为set集合
        this.excludeProperties = TextParseUtil.commaDelimitedStringToSet(excludeProperties);
    }

    public Set<String> getIncludeProperties() {
        return includeProperties;
    }

    public void setIncludeProperties(String includeProperties) {
        this.includeProperties = TextParseUtil.commaDelimitedStringToSet(includeProperties);
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * 该方法属于必定会执行的方法
     * @param invocation
     * @throws Exception
     */
    @Override
    public void execute(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();

        //只需要response对象,没有要request对象,因为数据
        HttpServletResponse response = (HttpServletResponse)actionContext.get(StrutsStatics
                .HTTP_RESPONSE);
        response.setContentType("text/json;charset=utf-8");

        //值栈查找
        Object rootObject = findRootObject(invocation);

        //序列化值栈查找的数据
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        if (includeProperties != null && includeProperties.size() != 0) {
            for (String in : includeProperties) {
                filter.getIncludes().add(in);
            }
        }
        if (excludeProperties != null && excludeProperties.size() != 0) {
            for (String ex : excludeProperties) {
                filter.getExcludes().add(ex);
            }
        }

        //执行序列化操作,并且写回去
        String jsonString = JSON.toJSONString(rootObject, filter);
        System.out.println("fastjson序列化的数据 " + jsonString);
        response.getWriter().println(jsonString);

    }


    /**
     * 搜索值栈,是否有root,是root对应的值还是栈顶的值,copy自JsonResult
     * @param invocation
     * @return
     */
    protected Object findRootObject(ActionInvocation invocation) {
        Object rootObject;
        if (this.root != null) {
            ValueStack stack = invocation.getStack();
            rootObject = stack.findValue(root);
        }
        else {
            rootObject = invocation.getStack().peek();
        }
        return rootObject;
    }
}
