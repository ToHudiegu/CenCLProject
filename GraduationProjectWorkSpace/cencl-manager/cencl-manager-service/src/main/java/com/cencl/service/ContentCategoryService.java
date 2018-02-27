package com.cencl.service;

import java.util.List;

import com.cencl.common.pojo.EUTreeNode;
import com.cencl.common.utils.CenclResult;

public interface ContentCategoryService {
	
	 List<EUTreeNode> getCategoryList(long parentId);
	 
	 //新增内容分类节点
	 CenclResult insertContentCategory(long parentId, String name);
	 
	 //删除内容分类节点
	 CenclResult deleteCategory(Long id);

	 //重命名节点
	 CenclResult updateCategory(Long id, String name);
	 

}
