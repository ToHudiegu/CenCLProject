package com.cencl.service;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.pojo.TbContent;

public interface ContentService {
	//增
	CenclResult insertContent(TbContent tbContent);
	
	//删
	CenclResult deleteItem(Long id);
	
	//改
	CenclResult editContent(TbContent tbContent);
	
	//查
	EUDataGridResult getItemList(Integer page, Integer rows, Long categoryId);
	
	
}
