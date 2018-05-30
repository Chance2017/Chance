package com.chance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.pojo.TbItem;
import com.chance.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@ResponseBody
	@RequestMapping("/item/{itemId}")
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
	
	@ResponseBody
	@RequestMapping("/item/list")
	public EasyUIDataGridResult getItemsList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	private ChanceResult createItem(TbItem item, String desc) throws Exception {
		ChanceResult result = itemService.createItem(item, desc);
		return result;
	}
	
}
