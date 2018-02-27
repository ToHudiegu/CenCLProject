package com.cencl.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cencl.common.utils.CenclResult;
import com.cencl.pojo.TbUser;

/* *
* <p>Title: </p>
* <p>Description: </p>
* <p>Company: Nothing</p>
* @author:  phubing
* @date: 2017年12月20日上午11:18:20
* version 0.0.1
*/
public interface UserService {
	//用户数据校验
	CenclResult checkData(String content, Integer type);
	
	//用户注册
	CenclResult createUser(TbUser tbUser);
	
	//用户登录接口
	CenclResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);
	
	//查询token
	CenclResult getToken(String token);
	
	//用户退出
	CenclResult logout(String token);
	
}
