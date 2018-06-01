package com.chance.service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.pojo.TbItemParam;

public interface ItemParamService {
	EasyUIDataGridResult getItemParamList(int page, int rows);
	
	ChanceResult getItemParamByCid(long cid);
	
	ChanceResult insertItemParam(TbItemParam itemParam);
}
