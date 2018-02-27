package com.cencl.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.ExceptionUtil;
import com.cencl.search.pojo.SearchResult;
import com.cencl.search.service.SearchService;

/*
 * 接收查询参数:查询条件、page、rows
 * 调用service执行查询，返回一个查询结果对象
 * 把结果对象包装到CenclResult中，结果是JSON格式数据
 * 
 * 查询条件为空，则返回400，提示搜索条件不能为空
 * Page:默认为1
 * Rows:默认为60
 * */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/query", method=RequestMethod.GET)
	@ResponseBody
	public CenclResult search(@RequestParam("q")String queryString, 
			@RequestParam(defaultValue="1")Integer page, 
			@RequestParam(defaultValue="60")Integer rows) {
		//查询条件不能为空
		if (StringUtils.isBlank(queryString)) {
			return CenclResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
			
			searchResult = searchService.search(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return CenclResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return CenclResult.ok(searchResult);
		
	}
	
}
