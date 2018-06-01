package com.chance.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.common.utils.IDUtils;
import com.chance.mapper.TbItemDescMapper;
import com.chance.mapper.TbItemMapper;
import com.chance.mapper.TbItemParamItemMapper;
import com.chance.pojo.TbItem;
import com.chance.pojo.TbItemDesc;
import com.chance.pojo.TbItemExample;
import com.chance.pojo.TbItemExample.Criteria;
import com.chance.pojo.TbItemParamItem;
import com.chance.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(long itemId) {
		TbItemExample example = new TbItemExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list != null && list.size() > 0) {
			TbItem item = list.get(00);
			return item;
		}
		return null;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		// 通过分页插件设置分页
		PageHelper.startPage(page, rows);
		
		List<TbItem> list = itemMapper.selectByExample(example);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

	@Override
	public ChanceResult createItem(TbItem item, String desc, String itemParams) throws Exception {
		// item补全
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 设置商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		
		// 插入到数据库
		itemMapper.insert(item);
		
		// 添加商品描述信息
		ChanceResult result = insertItemDesc(itemId, desc);
		if(result.getStatus() != 200) {
			throw new Exception();
		}
		// 添加商品规格参数信息
		result = insertItemParamItem(itemId, itemParams);
		if(result.getStatus() != 200) {
			throw new Exception();
		}
		
		return ChanceResult.ok();
	}
	
	/**
	 * 添加商品描述的富文本信息
	 * @param itemId 商品id
	 * @param desc 商品描述
	 * @return
	 */
	private ChanceResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		
		itemDescMapper.insert(itemDesc);
		
		return ChanceResult.ok();
	}
	
	/**
	 * 添加商品规格信息
	 * @param itemId 商品id
	 * @param itemParam	商品规格参数信息
	 * @return
	 */
	private ChanceResult insertItemParamItem(Long itemId, String itemParams) {
		
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		
		itemParamItemMapper.insert(itemParamItem);
		
		return ChanceResult.ok();
	}
	
}
