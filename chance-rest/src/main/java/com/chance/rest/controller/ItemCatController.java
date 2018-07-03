package com.chance.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chance.rest.pojo.CatResult;
import com.chance.rest.service.ItemCatService;

/**
 * 商品分类列表 
 * @author Chance
 *
 */
@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/*
	 * 方法一，字符串拼接完成jsonp结果封装
	@RequestMapping(value = "/itemcat/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		// 把pojo转成字符串
		String json = JsonUtils.objectToJson(catResult);
		// 拼装返回值
		String result = callback + "(" + json + ");";
		
		return result;
	}*/
	
	/*
	 * 方法二：通过SpringMVC 4.1之后的版本提供的方法，完成jsonp结果封装
	 */
	@RequestMapping(value = "/itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
}
