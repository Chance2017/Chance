package com.chance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chance.service.ItemParamItemService;

/**
 * 展示具体商品的规格参数
 * @author Chance
 *
 */
@Controller
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/showItem/{itemId}")
	public String showItemParam(@PathVariable long itemId, Model model) {
		String itemParam = itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("itemParam", itemParam);
		return "item";
	}
}
