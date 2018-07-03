package com.chance.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chance.common.pojo.ChanceResult;
import com.chance.common.pojo.EasyUIDataGridResult;
import com.chance.common.utils.ExceptionUtil;
import com.chance.common.utils.HttpClientUtil;
import com.chance.mapper.TbContentMapper;
import com.chance.pojo.TbContent;
import com.chance.pojo.TbContentExample;
import com.chance.pojo.TbContentExample.Criteria;
import com.chance.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC}")
	private String REST_CONTENT_SYNC;

	/**
	 * 获取内容项列表
	 * 
	 * @param page
	 *            列表的页码
	 * @param rows
	 *            一页多少行
	 */
	@Override
	public EasyUIDataGridResult getContentList(int page, int rows, Long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);

		// 设置分页
		PageHelper.startPage(page, rows);

		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);

		// 封装返回值
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

	/**
	 * 向内容表中新增数据
	 * 
	 * @param content
	 *            前台传入的TbContent对象
	 */
	@Override
	public ChanceResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);

		// 同步redis缓存
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
			return ChanceResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return ChanceResult.ok();
	}

	/**
	 * 修改内容项信息
	 * 
	 * @param content
	 *            前台传入的TbContent对象
	 * 
	 */
	@Override
	public ChanceResult editContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(content);

		// 同步redis缓存
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
			return ChanceResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return ChanceResult.ok();
	}

	/**
	 * 删除多个选中的内容项
	 * 
	 * @param ids
	 *            待删除的内容项的主键id列表
	 * 
	 */
	@Override
	public ChanceResult deleteContent(Long[] ids) {
		for (Long id : ids) {
			Long categoryId = contentMapper.selectByPrimaryKey(id).getCategoryId();
			contentMapper.deleteByPrimaryKey(id);
			
			// 同步redis缓存
			try {
				HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC + categoryId);
			} catch (Exception e) {
				e.printStackTrace();
				return ChanceResult.build(500, ExceptionUtil.getStackTrace(e));
			}
			
		}


		return ChanceResult.ok();
	}

}
