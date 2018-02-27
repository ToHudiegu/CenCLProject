package com.cencl.search.service;

import com.cencl.search.pojo.SearchResult;

public interface SearchService {
	//返回的应该是一个SearchResult对象
	SearchResult search(String queryString, int page, int rows) throws Exception;
	
}
