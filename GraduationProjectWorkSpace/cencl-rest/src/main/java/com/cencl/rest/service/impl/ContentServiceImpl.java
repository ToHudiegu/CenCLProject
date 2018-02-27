package com.cencl.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.JsonUtils;
import com.cencl.mapper.TbContentMapper;
import com.cencl.pojo.TbContent;
import com.cencl.pojo.TbContentExample;
import com.cencl.pojo.TbContentExample.Criteria;
import com.cencl.rest.dao.JedisClient;
import com.cencl.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("{INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	//接收分类内容id，根据分类id查询分类列表，返回一个pojo内容列表(使用http协议传输JSON数据)
	//根据内容分类id查询内容列表
	public List<TbContent> getContentList(long contentCid) {
		//从缓存中取内容
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid + "");
			
			//判断result是否有值
			if (result != null) {
				//把字符串转换成list
				List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
				return resultList;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//为空则执行下面查询
		
		//根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		//执行查询(大广告位)
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//向缓存中添加缓存
		try {
			//list为对象，所以先将list转换成一个字符串
			String cacheString = JsonUtils.objectToJson(list);
			//hkey为常亮，key为内容分类Id，value为cacheString
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCid +"", cacheString);
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public CenclResult syncContent(Long cid) {
		jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		
		return CenclResult.ok();
	}

}
