package com.chance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.pojo.TbContent;
import com.chance.service.ContentService;

@RequestMapping("/content")
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("query")
	@ResponseBody
	public EasyUIDataGridResult getContentList(int page, int rows, Long categoryId) {
		EasyUIDataGridResult result = contentService.getContentList(page, rows, categoryId);
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ChanceResult insertContent(TbContent content) {
		ChanceResult result = contentService.insertContent(content);
		return result;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public ChanceResult editContent(TbContent content) {
		ChanceResult result = contentService.editContent(content);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ChanceResult deleteContent(Long[] ids) {
		ChanceResult result = contentService.deleteContent(ids);
		return result;
	}
}
