package com.cencl.rest.service;

import com.cencl.common.utils.CenclResult;

public interface RedisService {

	CenclResult syncContent(long contentCid);
}
