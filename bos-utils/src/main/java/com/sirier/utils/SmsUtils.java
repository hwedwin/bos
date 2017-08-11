package com.sirier.utils;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.sirierx.sms.SendSmsUtils;

/**
 * Created by Sirierx on 2017/8/11.
 */

public class SmsUtils {

    public static SendSmsResponse sendSmsToSomeOne(String telephone, String name) {
        SendSmsResponse response = SendSmsUtils.sendSms(telephone, name);

        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

        return response;
    }

}
