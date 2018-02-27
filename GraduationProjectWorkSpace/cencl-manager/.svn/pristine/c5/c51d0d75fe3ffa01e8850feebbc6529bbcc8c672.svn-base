package com.cencl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cencl.service.ItemParamItemService;

//接收商品id，调用service查询规格参数，返回一个逻辑视图
@Controller
public class ItemParamItemContoller {

	@Autowired
	private ItemParamItemService itemParamItemService;

	@RequestMapping("/showItem/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String string = itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("itemParam", string);
		return "item";
	}
}
