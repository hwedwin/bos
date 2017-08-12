package com.sirier.activeMQ.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by Sirierx on 2017/8/12.
 */

@Component("queueSender")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-activeMQ.xml")
public class QueueSender {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;


    public void sendText(String queueName, final String message) {
        jmsTemplate.send(queueName, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
    public void sendMap(String queueName, final Map<String, String> map) {
        jmsTemplate.send(queueName, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                for (String key : map.keySet()) {
                    message.setString(key, map.get(key));
                }
                return message;
            }
        });
    }

    @Test
    public void fun() {
        for (int i = 0; i < 10; i++) {
            sendText("queueText", "hello");
        }

        Map<String,String> map = new HashMap<>();
        map.put("1","one");
        map.put("2","two");
        map.put("3","three");
        sendMap("queueMap",map);

    }


}
