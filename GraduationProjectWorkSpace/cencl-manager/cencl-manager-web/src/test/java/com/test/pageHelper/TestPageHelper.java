package com.test.pageHelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cencl.mapper.TbItemMapper;
import com.cencl.pojo.TbItem;
import com.cencl.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TestPageHelper {
	
	@Test
	public void testPageHelper(){
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		//从spring容器中获得mapper的代理对象
		TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
		
		//执行查询并分页
		TbItemExample example = new TbItemExample();
		
		PageHelper.startPage(1, 10);
		
		List<TbItem> list = tbItemMapper.selectByExample(example);
		
		for (TbItem tbItem : list) {
			
			System.out.println(tbItem.getTitle());
		}
		
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		long total = pageInfo.getTotal();
		
		System.out.println("共有商品"+total);
		
	}
	
}
