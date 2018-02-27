package com.cencl.service;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.pojo.TbItemParam;

public interface ItemParamService {
	CenclResult getItemParamById(long id);
	
	CenclResult insertItemParam(TbItemParam tbItemParam);
	
	public EUDataGridResult getItemList(Integer page, Integer rows);
	
	public CenclResult deleteItemParam(Long cid);
}
