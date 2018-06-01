package com.chance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.pojo.TbItemParam;
import com.chance.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	@ResponseBody
	@RequestMapping("/list")
	public EasyUIDataGridResult getItemsList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
		System.out.println(result.getTotal() + ":" + result.getRows());
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/query/itemcatid/{itemCatId}")
	public ChanceResult getItemParamByCid(@PathVariable long itemCatId) {
		ChanceResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/save/{cid}")
	public ChanceResult insertItemParam(@PathVariable long cid, String paramData) {
		// 创建pojo对象
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		
		ChanceResult result = itemParamService.insertItemParam(itemParam);
		return result;
	}	
}
