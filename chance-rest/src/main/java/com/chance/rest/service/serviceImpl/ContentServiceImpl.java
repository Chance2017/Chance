package com.chance.rest.service.serviceImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chance.common.utils.JsonUtils;
import com.chance.mapper.TbContentMapper;
import com.chance.pojo.TbContent;
import com.chance.pojo.TbContentExample;
import com.chance.pojo.TbContentExample.Criteria;
import com.chance.rest.dao.JedisClient;
import com.chance.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Override
	public List<TbContent> getContentList(long contentCategoryId) {
		
		// 从缓存中取内容
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "");
			if(!StringUtils.isBlank(result)) {
				// 把字符串转换成list
				List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 根据内容分类id，查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCategoryId);
		
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		
		// 向缓存中添加内容
		try {
			// 将list转换为字符串
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
