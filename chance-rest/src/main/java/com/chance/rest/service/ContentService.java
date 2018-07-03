package com.chance.rest.service;

import java.util.List;

import com.chance.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(long contentCategoryId);
}
