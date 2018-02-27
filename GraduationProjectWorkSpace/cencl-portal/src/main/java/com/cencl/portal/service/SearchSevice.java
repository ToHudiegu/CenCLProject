package com.cencl.portal.service;

import com.cencl.portal.pojo.SearchResult;

public interface SearchSevice {
	SearchResult search(String queryString, int page);
}
