package com.chance.service;

import java.util.List;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUITreeNode;

public interface ContentCategoryService {
	List<EasyUITreeNode> getCategoryList(long parentId);
	ChanceResult insertContentCategoryService(long parentId, String name);
	ChanceResult deleteContentCategoryService(long id);
	ChanceResult updateContentCategoryService(long id, String name);
}
