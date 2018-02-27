package com.cencl.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.HttpClientUtil;
import com.cencl.portal.pojo.SearchResult;
import com.cencl.portal.service.SearchSevice;

/*
 * 接收两个参数1、查询条件2、页码。调用taotao-search的搜索服务。
 * 接收返回的json数据，把json转换成java对象返回SearchResult对象。
 * */
@Service
public class SearchServiceImpl implements SearchSevice{
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String queryString, int page) {
		//查询参数
		Map<String, String> param= new HashMap<>();
		//查询条件
		param.put("q", queryString);
		param.put("page", page + "");
		
		try {
			//调用Cencl-Search服务
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			
			//将字符串转换成Java对象
			CenclResult cenclResult = CenclResult.formatToPojo(json, SearchResult.class);
			
			//转换成功
			if(cenclResult.getStatus() == 200){
				SearchResult result = (SearchResult) cenclResult.getData();
			
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//出错则返回空
		return null;
	}

}
