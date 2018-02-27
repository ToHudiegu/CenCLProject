package com.cencl.service;

import java.util.List;

import com.cencl.common.pojo.EUTreeNode;

public interface ItemCatService {
	List<EUTreeNode> getCatList(long parentId);
}
