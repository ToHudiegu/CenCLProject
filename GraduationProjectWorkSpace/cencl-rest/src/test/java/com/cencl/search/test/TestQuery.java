package com.cencl.search.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

//参数应该是SolrQuery。返回商品列表、查询结果总记录数
public class TestQuery {
	@Test
	public void queryDocument()throws Exception{
		//创建一个连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.224:8018/solr");
//		SolrServer solrServer = new HttpSolrServer("http://192.168.43.242:8018/solr");
		
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		
		//设置查询条件
		query.setQuery("*:*");
		query.setStart(20);
		query.setRows(50);
		
		//执行查询
		QueryResponse response = solrServer.query(query);
		
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		
		System.out.println("共查询到 " + solrDocumentList.getNumFound() + "记录");
		
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));

		}
		
	}
	
}
