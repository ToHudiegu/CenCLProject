package com.cencl.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cencl.common.utils.CenclResult;
import com.cencl.rest.service.RedisService;

//接收内容分类id，调用Service返回CenclResult
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/content/{contentCid}")
	@ResponseBody
	public CenclResult contentCacheSync(@PathVariable Long contentCid) {
		CenclResult result = redisService.syncContent(contentCid);
		return result;
	}
}