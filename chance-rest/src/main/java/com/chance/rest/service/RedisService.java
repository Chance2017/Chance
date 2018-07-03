package com.chance.rest.service;

import com.chance.common.pojo.ChanceResult;

public interface RedisService {
	ChanceResult syncContent(long contentCategoryId);
}
