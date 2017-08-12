package com.sirier.redis;

import com.alibaba.fastjson.serializer.SerializeFilter;

/**
 * redis数据库操作json字符串的crud
 *
 */
public interface RedisCRUD {
	/**
	 * 基于fastjson完成json字符串存储到redis数据库上
	 * 
	 * @param key
	 * @param jsonString
	 */
	public void writeJsonToRedis(String key, String jsonString);

	public void writeObjectToRedisByFastjson(String key, Object obj);

	public void writeObjectIncludesToRedisByFastjson(String key, Object obj, SerializeFilter filter);

	public void writeObjectIncludesToRedisByFastjson(String key, Object obj, String... properties);

	public String GetJsonFromRedis(String key);

	public void deleteJsonFromRedisByKey(String key);

}
