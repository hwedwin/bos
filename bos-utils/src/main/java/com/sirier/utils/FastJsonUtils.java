package com.sirier.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.Collections;
import java.util.Set;

/**
 * Created by Sirierx on 2017/8/5.
 */

public class FastJsonUtils {

    /**
     * 序列化任何对象,包含所有字段
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        // 使用这个SerializerFeature.xxx,关闭循环对象引用,但是容易出现栈溢出
        // String jsonString = JSON.toJSONString(object, SerializerFeature
        // .DisableCircularReferenceDetect);
        String jsonString = JSON.toJSONString(object);
        return jsonString;
    }

    /**
     * 序列化指定对象,仅仅包含传入的字段
     * @param object
     * @param properties
     * @return
     */
    public static String toJsonWithProperty(Object object, String... properties) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(properties);
        String jsonString = JSON.toJSONString(object, filter);
        return jsonString;
    }

    /**
     * 序列化所有对象,排除传入的字段
     * @param object
     * @param properties
     * @return
     */
    public static String toJsonWithoutProperty(Object object, String... properties) {
        //--------------------------------------------->既然是排除,这里不可以有参数
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        Set<String> excludes = filter.getExcludes();
        // for (String property : properties) {
        //     excludes.add(property)
        // }
        Collections.addAll(excludes, properties);
        String jsonString = JSON.toJSONString(object, filter);
        return jsonString;
    }

    // @Test
    // public void fun() {
    //     User user = new User();
    //     user.setId(1);
    //     user.setUsername("hello world");
    //     user.setGender("男");
    //     user.setRemark("hello");
    //
    //     System.out.println(toJson(user));
    //     System.out.println(toJsonWithProperty(user, "id", "username"));
    //     System.out.println(toJsonWithoutProperty(user, "username"));
    // }
}
