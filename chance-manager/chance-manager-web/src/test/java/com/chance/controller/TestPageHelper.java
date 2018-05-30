package com.chance.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.chance.mapper.TbItemMapper;
import com.chance.pojo.TbItem;
import com.chance.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TestPageHelper {
	
	@Test
	public void test() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		
		// 利用PageHelper分页插件来控制数据库只取一页的数据，第一个参数表示第几页，第二个参数表示一页有多少个数据
		PageHelper.startPage(2, 10);
		
		List<TbItem> list = mapper.selectByExample(example);
		// 取商品列表
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getId() + ":" + tbItem.getTitle());
		}
		// 取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println("共有商品：" + pageInfo.getTotal());
	}
}
