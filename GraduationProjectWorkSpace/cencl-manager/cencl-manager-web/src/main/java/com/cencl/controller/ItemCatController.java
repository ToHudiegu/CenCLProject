package com.cencl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cencl.common.pojo.EUTreeNode;
import com.cencl.service.ItemCatService;

/*
 * 商品分类管理Controller
 * 		接收页面名为id的请求参数，调用service查询分类列表，返回JSON格式列表
 * */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	//有参数则传递id值，否则默认为0
	public List<EUTreeNode> getCatList(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EUTreeNode> list = itemCatService.getCatList(parentId);
		
		return list;
	}
	
}
