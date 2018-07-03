package com.chance.search.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	
	/**
	 * 添加一个文档
	 * @throws Exception
	 */
	@Test
	public void addDocument() throws Exception {
		// 创建一个连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		// 创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品1");
		document.addField("item_price", 12345);
		// 把文档对象写入数据库
		solrServer.add(document);
		// 提交
		solrServer.commit();
	}
	
	/**
	 * 修改一个文档，还是依赖于添加，添加时只要id相同就会修改
	 * @throws Exception
	 */
	@Test
	public void updateDocument() throws Exception {
		// 创建一个连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		// 创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品2");
		document.addField("item_price", 54321);
		// 把文档对象写入数据库
		solrServer.add(document);
		// 提交
		solrServer.commit();
	}
	
	/**
	 * 删除文档
	 * @throws Exception
	 */
	@Test
	public void deleteDocument() throws Exception {
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr");
		server.deleteById("test001");
//		server.deleteByQuery("*:*");
		server.commit();
	}
	
	/**
	 * 查询文档
	 */
	@Test
	public void queryDocument() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		SolrQuery query = new SolrQuery();
		
		query.setQuery("*:*");	// 设置查询条件
		query.setStart(20);		// 设置开始页
		query.setRows(20);		// 设置每页多少条数据
		
		QueryResponse response = solrServer.query(query);
		SolrDocumentList resultList = response.getResults();
		
		System.out.println("共查询到：" + resultList.getNumFound() + "条记录");
		
		for (SolrDocument solrDocument : resultList) {
			System.out.print(solrDocument.get("id") + "===");
			System.out.print(solrDocument.get("item_title") + "===");
			System.out.print(solrDocument.get("item_price") + "===");
			System.out.println(solrDocument.get("item_image") + "===");
		}
	}
	
}
