package com.sirier.service.impl;

import com.sirier.dao.IDecidedzoneDao;
import com.sirier.dao.IOrderDao;
import com.sirier.dao.IRegionDao;
import com.sirier.dao.IWorkBillDao;
import com.sirier.domain.Customer;
import com.sirier.domain.DecidedZone;
import com.sirier.domain.Order;
import com.sirier.domain.Region;
import com.sirier.domain.Staff;
import com.sirier.domain.Subarea;
import com.sirier.domain.WorkBill;
import com.sirier.service.CustomerService;
import com.sirier.service.OrderService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by Sirierx on 2017/8/15.
 */

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private IDecidedzoneDao decidedzoneDao;
    @Autowired
    private IWorkBillDao workBillDao;
    @Autowired
    private IRegionDao regionDao;
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;


    @Override
    public void Save(Order order) {
        System.out.println("order = " + order);
        //自动分单业务
        //抽取-->封装workbill对象,MQ发短信->新模版

        //鉴于order是主键自增-->使用saveAndFlush()->使得对象save后被赋予主键值
        //且该order有主键,是持久态,事务关闭时自动提交,不需要手动再去save()
        orderDao.saveAndFlush(order);

        //拿order的address信息
        //1-地址库全匹配-->crm
        //获取发件人的省市区去匹配 // 安徽省/滁州市/定远县
        String sendAreaInfo = order.getSendAreaInfo();
        String province = sendAreaInfo.split("/")[0];
        String city = sendAreaInfo.split("/")[1];
        String district = sendAreaInfo.split("/")[2];

        String allAddress = province + city + district + order.getSendAddress();
        //这个是省市区+详细地址(详细地址可能也含有市区级信息)
        //  String s = URLEncoder.encode("北京市北京市西城区北京市西城区长安街100号店");
        Customer customer = customerService.getCustomerByAddress(allAddress);
        System.out.println("customer = " + customer);
        if (customer != null) {
            //匹配到了-->地址库完全匹配成功
            String decidedzoneId = customer.getDecidedzoneId();
            if (StringUtils.isNotBlank(decidedzoneId)) {
                DecidedZone decidedZone = decidedzoneDao.findOne(decidedzoneId);
                Staff staff = decidedZone.getStaff();
                generateWorkBill(order, staff); //开始封装workbill

                order.setStaff(staff);
                order.setOrderType("自动分单1");//order无须手动保存,事务关闭时会自动关闭

                //把短信内容放到mq中,然后在短信模块接收mq,那边发短信,这边只管封装
                //sendSuccessSmsByMQ();
                sendSuccessSmsByMQ(order, staff);

                return;//成功了就直接return掉
            }
        }

        //2-分区模糊匹配-->
        //没匹配到,开始做分区模糊匹配
        //理论上会有各种辅助关键字,门牌号等等,这里从简
        //匹配区域
        Region region = regionDao.findRegionByProvinceAndCityAndDistinct(province, city,
                district);
        //获取发件人的详细地址去匹配
        String sendAddress = order.getSendAddress();
        if (region != null) {
            Set<Subarea> subareas = region.getSubareas();//区域下面必定要有分区的
            if (subareas != null) {
                for (Subarea subarea : subareas) {
                    if (sendAddress.contains(subarea.getAddresskey())) {
                        //包含关键字就算匹配上了吧
                        //能拿到数据吗???
                        Staff staff = subarea.getDecidedZone().getStaff();
                        generateWorkBill(order, staff);

                        order.setStaff(staff);
                        order.setOrderType("自动分单2");
                        //order自动提交保存
                        //sendSuccessSmsByMQ();
                        sendSuccessSmsByMQ(order, staff);
                    }
                }
            }
            return;
        }
        //如果自动分单都成功了,那肯定不会到这里
        order.setOrderType("人工调度");
    }

    private void sendSuccessSmsByMQ(Order order, Staff staff) {
        final HashMap<String,String> map = new HashMap<>();
        //存放的key和短信模版最好一样
        map.put("telephone", staff.getTelephone());
        map.put("sendName", order.getSendName());
        map.put("date", new Date().toString());
        map.put("address", order.getRecAddress());

        jmsTemplate.send("successSms", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                for (String key : map.keySet()) {
                    message.setString(key, map.get(key));
                }
                return message;
            }
        });
    }


    /**
     * 封装staff和order中的数据到workbill对象
     * @param order
     * @param staff
     */
    private void generateWorkBill(Order order, Staff staff) {
        WorkBill workBill = new WorkBill();
        workBill.setAttachbilltimes(0);
        workBill.setBuildtime(new Date(System.currentTimeMillis()));
        workBill.setOrder(order);// 工单关联订单 需要订单的外键
        workBill.setPickstate("已下单");
        workBill.setRemark(order.getRemark());
        workBill.setSmsNumber(staff.getId() + "_" + UUID.randomUUID().toString());
        workBill.setStaff(staff);
        workBill.setType("新单");
        workBillDao.save(workBill);
    }
}
