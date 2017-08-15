package com.sirierx.activeMQ.consumter;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.sirierx.sms.SendSmsUtils;

import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by Sirierx on 2017/8/12.
 */

@Component("queueReceiver2")
public class QueueReceiver2 implements MessageListener {
    public void onMessage(Message message) {

        String telephone = null;
        String sendName = null;
        String date = null;
        String address = null;
        MapMessage mapMessage = (MapMessage)message;
        try {
            telephone = mapMessage.getString("telephone");
            sendName = mapMessage.getString("sendName");
            date = mapMessage.getString("date");
            address = mapMessage.getString("address");

            SendSmsResponse response = SendSmsUtils.sendSms2(telephone, sendName, " ", address);

            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
