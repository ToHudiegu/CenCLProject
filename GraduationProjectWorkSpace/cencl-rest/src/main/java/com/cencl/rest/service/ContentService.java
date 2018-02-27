package com.cencl.rest.service;

import java.util.List;

import com.cencl.common.utils.CenclResult;
import com.cencl.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(long contentCid);
	
	CenclResult syncContent(Long cid);
	
}