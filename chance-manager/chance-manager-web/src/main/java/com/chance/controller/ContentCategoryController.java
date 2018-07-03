package com.chance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUITreeNode;
import com.chance.service.ContentCategoryService;

/**
 * 内容分类管理（CMS）
 * @author Chance
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
		
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public ChanceResult createContentCategory(Long parentId, String name) {
		ChanceResult result = contentCategoryService.insertContentCategoryService(parentId, name);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ChanceResult deleteContentCategory(Long id) {
		ChanceResult result = contentCategoryService.deleteContentCategoryService(id);
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ChanceResult updateContentCategory(Long id, String name) {
		ChanceResult result = contentCategoryService.updateContentCategoryService(id, name);
		return result;
	}
	
}
