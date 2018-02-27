package com.cencl.rest.service;

import com.cencl.common.utils.CenclResult;

public interface ItemService {

	CenclResult getItemBaseInfo(long itemId);
	CenclResult getItemDesc(long itemId);
	CenclResult getItemParam(long itemId);
}
