package com.chance.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.utils.ExceptionUtil;
import com.chance.pojo.TbContent;
import com.chance.rest.service.ContentService;

/**
 * 内容管理Controller
 * @author Chance
 *
 */
@RequestMapping("/content")
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public ChanceResult getContentList(@PathVariable Long contentCategoryId) {
		try {
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return ChanceResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ChanceResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
