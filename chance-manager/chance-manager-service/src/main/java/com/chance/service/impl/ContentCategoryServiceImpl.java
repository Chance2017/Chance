package com.chance.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUITreeNode;
import com.chance.mapper.TbContentCategoryMapper;
import com.chance.pojo.TbContentCategory;
import com.chance.pojo.TbContentCategoryExample;
import com.chance.pojo.TbContentCategoryExample.Criteria;
import com.chance.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	TbContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 获取内容分类列表
	 */
	@Override
	public List<EasyUITreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		List<EasyUITreeNode> resultList = new ArrayList<>();
		
		for (TbContentCategory contentCategory : list) {
			// 创建一个节点
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		
		return resultList;
	}

	/**
	 * 插入一个新的内容分类项
	 */
	@Override
	public ChanceResult insertContentCategoryService(long parentId, String name) {
		// 创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setCreated(new Date());
		
		// 添加到数据库中
		contentCategoryMapper.insert(contentCategory);
		
		// 查看父节点的isParent是否为true，如果不是则改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		
		return ChanceResult.ok(contentCategory);
	}

	/**
	 * 删除某个内容分类项
	 */
	@Override
	public ChanceResult deleteContentCategoryService(long id) {
		// 查出其父id
		TbContentCategory toDeleteCat = contentCategoryMapper.selectByPrimaryKey(id);
		long parentId = toDeleteCat.getParentId();
		
		// 从数据库中删除
		contentCategoryMapper.deleteByPrimaryKey(id);
		
		// 该记录删除后，判断其父节点下是否还有其他结点，没有就将其isParent属性设置为false
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		if( contentCategoryMapper.selectByExample(example) == null ) {
			TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
			parentCat.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		
		return ChanceResult.ok();
	}

	/**
	 * 重命名某个内容分类项
	 */
	@Override
	public ChanceResult updateContentCategoryService(long id, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return ChanceResult.ok();
	}
	
}
