package com.cencl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cencl.common.pojo.EUTreeNode;
import com.cencl.common.utils.CenclResult;
import com.cencl.service.ContentCategoryService;

/*
 * 内容分类管理：
 * 接收页面传递过来parentId，根据parentId查询节点列表，返回List<EUTreeNode>，需要相应JSON数据
 * */

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
     @ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	//接收两个参数parentid、name。调用Service添加记录。返回CenclResult。应该返回json数据
	@RequestMapping("/create")
	@ResponseBody
	public CenclResult createContentCategory(Long parentId, String name) {
		CenclResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}

	
	@RequestMapping("/delete")
	@ResponseBody
	public CenclResult deleteContentCategory(Long id){
		CenclResult result = contentCategoryService.deleteCategory(id);
		
		return result;
	}
	
	
	@RequestMapping("/update")
	@ResponseBody
	public CenclResult updateContentCategory(Long id, String name){
		CenclResult result = contentCategoryService.updateCategory(id, name);
		
		return result;
	}
	
}
