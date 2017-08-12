package com.sirier.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * 基于fastjson序列化数据存于redis
 */
@Repository
public class RedisCRUDImpl implements RedisCRUD {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void writeJsonToRedis(String key, String jsonString) {
        redisTemplate.opsForValue().set(key, jsonString);
    }

    public void writeObjectToRedisByFastjson(String key, Object obj) {
        String jsonString = JSON.toJSONString(obj);
        redisTemplate.opsForValue().set(key, jsonString);
    }

    public void writeObjectIncludesToRedisByFastjson(String key, Object obj, SerializeFilter
            filter) {
        String jsonString = JSON.toJSONString(obj, filter);
        redisTemplate.opsForValue().set(key, jsonString);
    }

    public void writeObjectIncludesToRedisByFastjson(String key, Object obj, String...
            properties) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(properties);
        String jsonString = JSON.toJSONString(obj, filter);
        redisTemplate.opsForValue().set(key, jsonString);
    }

    public String GetJsonFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteJsonFromRedisByKey(String key) {
        redisTemplate.delete(key);
    }

}
