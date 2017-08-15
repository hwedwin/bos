package com.sirier.web.action;

import com.sirier.domain.Customer;
import com.sirier.utils.RandStringUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by Sirierx on 2017/8/13.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("bos-foreground")
public class CustomerAction extends BaseAction<Customer> {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 接收页面的telephone,发送短信
     * @return
     * @throws Exception
     */
    @Action(value = "customerAction_sendCode", results = { @Result(name = "sendCode", type =
            "json") })
    public String sendCode() {
        System.out.println(model);
        try {
            //往redis存一份
            final String telephone = model.getTelephone();
            final String checkCode = RandStringUtils.getCode();
            redisTemplate.opsForValue().set(telephone, checkCode, 120, TimeUnit.SECONDS);

            //往activeMQ发一份
            jmsTemplate.send("checkCode", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("telephone", telephone);
                    mapMessage.setString("checkCode", checkCode);
                    return mapMessage;
                }
            });
            push(true);
        } catch (Exception e) {
            push(false);
            e.printStackTrace();
        }

        return "sendCode";
    }


}
