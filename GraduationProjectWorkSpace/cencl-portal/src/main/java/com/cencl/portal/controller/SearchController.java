package com.cencl.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cencl.portal.pojo.SearchResult;
import com.cencl.portal.service.SearchSevice;

/*
 * 接收请求的参数：页码和查询条件，调用Service查询商品列表得到SearchResult对象
 * 把
 * Query：回显的查询条件
 * totalPages：总页数
 * itemList：商品列表
 * Page：当前页码
 * 传递到页面。返回一个逻辑视图search字符串
 * */
@Controller
public class SearchController {
	@Autowired
	private SearchSevice searchSevice;
	
	@RequestMapping("/search")
	//queryString与查询的参数名不一样，需要使用RequestParam转换
	public String search(@RequestParam("q")String queryString, @RequestParam(defaultValue="1")Integer page, Model model){
		//空指针异常处理
		if(queryString != null){
			try {
				queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//调用service方法
		SearchResult searchResult = searchSevice.search(queryString, page);
		
		//向页面传递查询参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		
		return "search";
	}
	
}
