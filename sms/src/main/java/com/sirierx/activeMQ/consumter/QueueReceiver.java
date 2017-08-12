package com.sirierx.activeMQ.consumter;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Sirierx on 2017/8/12.
 */

@Component("queueReceiver")
public class QueueReceiver implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void QueueReceiverTest() {
        String USERNAME = ActiveMQConnection.DEFAULT_USER;// 默认连接用户名
        String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;// 默认连接密码
        String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;// 默认连接地址


        ConnectionFactory connectionFactory;// 连接工厂
        Connection connection = null;// 连接

        Session session;// 会话 接受或者发送消息的线程
        Destination destination;// 消息的目的地

        MessageConsumer messageConsumer;// 消息的消费者

        // 实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);

        try {
            // 通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            // 启动连接
            connection.start();
            // 创建session false 表示是否启用事务 如果true 操作消息后,必须调用session.commit方法
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 创建一个连接HelloWorld的消息队列
            // destination = session.createQueue("queueText");
            // // 创建消息消费者
            // messageConsumer = session.createConsumer(destination);
            //
            // while (true) {
            //     TextMessage textMessage = (TextMessage)messageConsumer.receive(100000);
            //     if (textMessage != null) {
            //         System.out.println("收到的消息:" + textMessage.getText());
            //     }
            //     else {
            //         break;
            //     }
            // }

            destination = session.createQueue("queueMap");
            // 创建消息消费者
            messageConsumer = session.createConsumer(destination);

            while (true) {
                MapMessage mapMessage = (MapMessage)messageConsumer.receive(100000);
                if (mapMessage != null) {
                    String s1 = mapMessage.getString("1");
                    String s2 = mapMessage.getString("2");
                    String s3 = mapMessage.getString("3");
                    System.out.println("s = " + s1 + "--" + s2 + "--" + s3);
                }
                else {
                    break;
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
