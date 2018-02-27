/*package com.cencl.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientPostWithParamTest {
	@Test
	public void postWithParamTest() throws Exception{
		//创建一个HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		//创建一个post对象
		HttpPost httpPost = new HttpPost("http://localhost:8050/httpClient/post.html");
		
		//创建一个Entity，模拟一个表单
		List<NameValuePair> kvList = new ArrayList<>();
		kvList.add(new BasicNameValuePair("username", "李彦宏"));
		kvList.add(new BasicNameValuePair("password", "root"));
		
		//包装成一个Entity对象
		StringEntity stringEntity = new UrlEncodedFormEntity(kvList);
		
		//设置请求的内容
		httpPost.setEntity(stringEntity);
		
		//执行post请求
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
		
		String string = EntityUtils.toString(closeableHttpResponse.getEntity());
		System.out.println(string);
		
		//关闭
		closeableHttpResponse.close();
		httpClient.close();
		
		
	}
	
	
}
*/