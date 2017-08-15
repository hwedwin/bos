package com.sirier.web.action;

import com.sirier.domain.Order;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ResourceBundle;

/**
 * Created by Sirierx on 2017/8/15.
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos-foreground")
public class OrderAction extends BaseAction<Order> {


    @Action(value = "orderAction_add",
            results = { @Result(name = "add", type = "redirect", location = "/index.html") })
    public String add() {
        System.out.println(model);
        //拿着这个model跨域传送到后台系统
        String url = ResourceBundle.getBundle("cxfUrl").getString("foreground2bos_Order_Url");
        WebClient.create(url + "/order/save").post(model);

        return "add";
    }


}
