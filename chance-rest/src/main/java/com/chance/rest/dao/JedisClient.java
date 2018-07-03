package com.chance.rest.dao;

public interface JedisClient {
	
	String get(String key);
	String set(String key, String value);
	String hget(String hkey, String key);
	Long hset(String hkey, String key, String value);
	long del(String key);
	long hdel(String hkey, String key);
	/**
	 * 自增
	 * @param key
	 * @return
	 */
	long incr(String key);
	/**
	 * 设置过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	long expire(String key, int seconds);
	/**
	 * 设置当前距离过期剩余时间
	 * @param key
	 * @return
	 */
	long ttl(String key);
	
}
