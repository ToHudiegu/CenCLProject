package com.cencl.service;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long itemId);
	
	//查询商品列表
	EUDataGridResult getItemList(int page, int rows);
	
	//添加商品的同时，把商品描述也一并添加进去（保证两个表在同一个事务）
	CenclResult createItem(TbItem tbItem, String desc, String itemParam) throws Exception;
	
	//编辑商品
	CenclResult updateItem(TbItem tbItem, String desc, String itemParams);
	
	//删除商品
	CenclResult deleteItem(Long id);
	
	//获取商品id
	CenclResult getItem(Long id);
	
	//根据商品id查询商品列表
	CenclResult getItemQueryParam(Long itemId);
	
	//
	String getItemParamHtml(Long itemId);
}



