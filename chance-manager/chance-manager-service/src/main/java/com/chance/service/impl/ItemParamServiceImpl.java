package com.chance.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.mapper.TbItemParamMapper;
import com.chance.pojo.TbItemParam;
import com.chance.pojo.TbItemParamExample;
import com.chance.pojo.TbItemParamExample.Criteria;
import com.chance.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Override
	public EasyUIDataGridResult getItemParamList(int page, int rows) {
		TbItemParamExample example = new TbItemParamExample();
		// 通过分页插件设置分页
		PageHelper.startPage(page, rows);
		
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		
		result.setRows(list);
		
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

	@Override
	public ChanceResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0) {
			return ChanceResult.ok(list.get(0));
		}
		return ChanceResult.ok();
	}

	@Override
	public ChanceResult insertItemParam(TbItemParam itemParam) {
		// 补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		// 插入到数据库中
		itemParamMapper.insert(itemParam);
		return ChanceResult.ok();
	}

}
