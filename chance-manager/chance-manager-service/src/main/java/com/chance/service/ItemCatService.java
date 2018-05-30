package com.chance.service;

import java.util.List;

import com.chance.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> getItemCatList(long parentId)	;
}
