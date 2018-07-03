package com.chance.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.utils.HttpClientUtil;
import com.chance.common.utils.JsonUtils;
import com.chance.pojo.TbContent;
import com.chance.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
	@Override
	public String getContentList() {
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		
		try {
			ChanceResult chanceResult = ChanceResult.formatToList(result, TbContent.class);
			
			List<Map<String, Object>> resultList = new ArrayList<>();
			List<TbContent> list = (List<TbContent>)chanceResult.getData();
			
			for (TbContent tbContent : list) {
				Map<String, Object> map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("heightB", 240);
				map.put("widthB", 550);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				resultList.add(map);
			}
			
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
