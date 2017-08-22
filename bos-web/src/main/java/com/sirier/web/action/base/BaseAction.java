package com.sirier.web.action.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sirier.service.ManageService;
import com.sirier.utils.FileNameUtils;
import com.sirier.utils.LogUtils;
import com.sirier.utils.MyUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
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

    @Autowired
    protected ManageService manageService;

    /**
     * 获取运行期的类对象,给model实例化
     */
    public BaseAction() {
        // 对model进行实例化， 通过子类 类声明的泛型
        Type superclass = this.getClass().getGenericSuperclass();

        if (!(superclass instanceof ParameterizedType)) {
            superclass = this.getClass().getSuperclass().getGenericSuperclass();
        }


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


    /**
     * 把list数据转换为json数据,不添加泛型
     * @param list   任意类型的list数据
     * @param fields 不需要序列化的字段
     */
    public void list2Json(List list, String[] fields) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        Set<String> excludeSet = filter.getExcludes();
        //Collections.addAll(excludeSet,fields);
        for (int i = 0; i < fields.length; i++) {
            excludeSet.add(fields[i]);
        }
        //
        // for (String field : fields) {
        //     excludeSet.add(field);
        // }

        String jsonString = JSON.toJSONString(list, filter);
        LogUtils.getInstance().warn(jsonString);
        // ----------------把json写回去-----------------------------
        MyUtils.setJsonType();
        MyUtils.getWriter().write(jsonString);
    }


/**
 * 提供文件下载方法,内部实现两头一流的封装,文件名的兼容,采用response的输出流
 * @param filename 初始文件名
 * @param path     下载文件的url
 */
    /**
     * public void download(String filename, String path) {
     * HttpServletResponse response = getResponse();
     * try {
     * ServletContext context = ServletActionContext.getServletContext();
     * response.setHeader(
     * "Content-Disposition",
     * "attachment;filename=" +
     * FileNameUtils.encodeDownloadFilename(
     * filename, ServletActionContext.getRequest()
     * .getHeader("user-agent")));
     *
     * response.setContentType(context.getMimeType(filename));
     * //输出流
     * ServletOutputStream outputStream = response.getOutputStream();
     * //写入流,很多插件都有提供,这里是最底层的方式
     * InputStream in = new FileInputStream(path);
     * int len;
     * byte[] bytes = new byte[1024 * 8];
     * while ((len = in.read(bytes)) != -1) {
     * outputStream.write(bytes, 0, len);
     * }
     *
     * in.close();
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */


    protected void downloadSheet(String filename, HSSFWorkbook workbook) throws IOException {
        // -->设置mimetype
        String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
        ServletActionContext.getResponse().setContentType(mimeType);
        // -->根据user-agent获取不乱码的文件名
        String agent = ServletActionContext.getRequest().getHeader("User-Agent");
        filename = FileNameUtils.encodeDownloadFilename(filename, agent);
        // -->设置content-disposition
        ServletActionContext.getResponse().setHeader("content-disposition", "attachment;" +
                "filename=" + filename);
        // -->设置输出流-->直接获取的是响应的输出流
        ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
        // 输出,运用的是workbook自带的输出方式
        workbook.write(outputStream);
    }


    // 分页请求属性驱动  --->有set方法即可塞入数据了,不需要属性的定义
    protected int page; // 页码
    protected int rows; // 每页 记录数
    protected PageRequest pageRequest;  //分页请求的条件参数
    protected Page<T> pageData;//分页请求的

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * 调用该方法,即给pageRequest注入值
     */
    protected PageRequest getPageRequest() {
        pageRequest = new PageRequest(page - 1, rows);
        return pageRequest;
    }

    public void setPageData(Page<T> pageData) {
        this.pageData = pageData;
    }
    // root栈：action的属性
    // 这个result就是专门来存放结果集的,只要把页面需要的数据赋予result即可
    // 这个result放到了值栈里面,然后struts.xml配置<param name="root">result</param>
    // 即把参数root的值置为result,底层会通过root去找名为result的值

    protected Object result;

    /**
     * 专门用于接收分页数据的结果集,内置分页的字段优化
     * @return
     */
    public Object getResult() {
        //仅仅需要两个字段,且封装为map结果传递回去
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("total", pageData.getTotalElements());
        map.put("rows", pageData.getContent());
        return map;
    }


}
