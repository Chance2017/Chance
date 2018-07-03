package com.chance.service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.pojo.TbContent;

public interface ContentService {
	EasyUIDataGridResult getContentList(int page, int rows, Long categoryId);
	ChanceResult insertContent(TbContent content);
	ChanceResult editContent(TbContent content);
	ChanceResult deleteContent(Long[] ids);
}
