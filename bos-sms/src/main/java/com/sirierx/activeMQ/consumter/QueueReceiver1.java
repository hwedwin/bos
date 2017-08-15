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

@Component("queueReceiver1")
public class QueueReceiver1 implements MessageListener {
    public void onMessage(Message message) {

        String telephone = null;
        String checkCode = null;
        MapMessage mapMessage = (MapMessage)message;
        try {
            telephone = mapMessage.getString("telephone");
            checkCode = mapMessage.getString("checkCode");

            System.out.println("telephone = " + telephone);
            System.out.println("checkCode = " + checkCode);
            SendSmsResponse response = SendSmsUtils.sendSms1(telephone, checkCode);

            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //@Test
    // public void QueueReceiverTest() {
    //     String USERNAME = ActiveMQConnection.DEFAULT_USER;// 默认连接用户名
    //     String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;// 默认连接密码
    //     String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;// 默认连接地址
    //
    //
    //     ConnectionFactory connectionFactory;// 连接工厂
    //     Connection connection = null;// 连接
    //
    //     Session session;// 会话 接受或者发送消息的线程
    //     Destination destination;// 消息的目的地
    //
    //     MessageConsumer messageConsumer;// 消息的消费者
    //
    //     // 实例化连接工厂
    //     connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
    //
    //     try {
    //         // 通过连接工厂获取连接
    //         connection = connectionFactory.createConnection();
    //         // 启动连接
    //         connection.start();
    //         // 创建session false 表示是否启用事务 如果true 操作消息后,必须调用session.commit方法
    //         session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    //         // 创建一个连接HelloWorld的消息队列
    //         // destination = session.createQueue("queueText");
    //         // // 创建消息消费者
    //         // messageConsumer = session.createConsumer(destination);
    //         //
    //         // while (true) {
    //         //     TextMessage textMessage = (TextMessage)messageConsumer.receive(100000);
    //         //     if (textMessage != null) {
    //         //         System.out.println("收到的消息:" + textMessage.getText());
    //         //     }
    //         //     else {
    //         //         break;
    //         //     }
    //         // }
    //
    //         destination = session.createQueue("queueMap");
    //         // 创建消息消费者
    //         messageConsumer = session.createConsumer(destination);
    //
    //         while (true) {
    //             MapMessage mapMessage = (MapMessage)messageConsumer.receive(100000);
    //             if (mapMessage != null) {
    //                 String s1 = mapMessage.getString("1");
    //                 String s2 = mapMessage.getString("2");
    //                 String s3 = mapMessage.getString("3");
    //                 System.out.println("s = " + s1 + "--" + s2 + "--" + s3);
    //             }
    //             else {
    //                 break;
    //             }
    //         }
    //
    //     } catch (JMSException e) {
    //         e.printStackTrace();
    //     }
    //
    // }

}
