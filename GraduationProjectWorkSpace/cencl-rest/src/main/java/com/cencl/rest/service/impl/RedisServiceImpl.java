package com.cencl.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.ExceptionUtil;
import com.cencl.rest.dao.JedisClient;
import com.cencl.rest.service.RedisService;

//接收分类id，调用dao层，删除redis的对应hash中key为分类id的项
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClient jedisClient;

	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public CenclResult syncContent(long contentCid) {
		try {
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid + "");
		} catch (Exception e) {
			e.printStackTrace();
			return CenclResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return CenclResult.ok();
	}
}