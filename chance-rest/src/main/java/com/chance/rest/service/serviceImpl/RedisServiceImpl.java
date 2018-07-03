package com.chance.rest.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.utils.ExceptionUtil;
import com.chance.rest.dao.JedisClient;
import com.chance.rest.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Override
	public ChanceResult syncContent(long contentCategoryId) {
		try {
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "");
		} catch (Exception e) {
			e.printStackTrace();
			return ChanceResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return ChanceResult.ok();
	}

}
