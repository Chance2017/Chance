package com.chance.service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page, int rows);
	ChanceResult createItem(TbItem item, String desc) throws Exception;
}
