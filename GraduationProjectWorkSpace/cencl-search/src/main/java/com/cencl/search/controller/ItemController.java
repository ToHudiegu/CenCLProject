package com.cencl.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cencl.common.utils.CenclResult;
import com.cencl.search.service.ItemService;

// /search/manager/importAll
//发布一个REST形式的服务（无参数）,调用service服务方法，将数据导入索引库中，返回CenclResult
@Controller
@RequestMapping("/manager")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/importAll")
	@ResponseBody
	public CenclResult importAllItems(){
		//导入商品数据到索引库
		CenclResult result = itemService.importAllItems();
		
		return result;
	}
	
	
	
	
}
