package com.cencl.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.cencl.search.pojo.SearchResult;

public interface SearchDao {
	public SearchResult search(SolrQuery query) throws Exception;
}
