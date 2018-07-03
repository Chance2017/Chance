package com.chance.rest.service.serviceImpl;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chance.mapper.TbItemCatMapper;
import com.chance.pojo.TbItemCat;
import com.chance.pojo.TbItemCatExample;
import com.chance.pojo.TbItemCatExample.Criteria;
import com.chance.rest.pojo.CatNode;
import com.chance.rest.pojo.CatResult;
import com.chance.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		// 查询分类列表
		catResult.setData(getCatList(0));
		return catResult;
	}

	private List<?> getCatList(long parentId) {

		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		// 返回值
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 返回值list
		List<Object> resultList = new ArrayList<>();
		// 向list中添加节点
		int count = 0;
		for (TbItemCat itemCat : list) {
			// 判断是否为父节点
			if (itemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				} else {
					catNode.setName(itemCat.getName());
				}
				catNode.setUrl("/products/" + itemCat.getId() + ".html");
				catNode.setItem(getCatList(itemCat.getId()));

				resultList.add(catNode);
				
				// 由于页面大小需要，控制第一层只取14条数据
				count ++;
				if(parentId == 0 && count >= 14)
					break;
			}
			// 如果是叶子节点
			else {
				resultList.add("/products/" +itemCat.getId() + ".html|" + itemCat.getName());
			}
		}

		return resultList;

	}
}
