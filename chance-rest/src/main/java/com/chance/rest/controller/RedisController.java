package com.chance.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chance.common.pojo.ChanceResult;
import com.chance.rest.service.RedisService;

/**
 * 缓存同步Controller
 * @author Chance
 *
 */
@Controller
@RequestMapping("/cache")
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/sync/content/{contentCategoryId}")
	public ChanceResult contentCacheSync(@PathVariable Long contentCategoryId) {
		ChanceResult result = redisService.syncContent(contentCategoryId);
		return result;
	}
	
	
}
