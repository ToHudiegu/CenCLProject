package com.cencl.portal.service;

import com.cencl.pojo.TbUser;

/* *
* <p>Title: </p>
* <p>Description: </p>
* <p>Company: Nothing</p>
* @author:  phubing
* @date: 2017年12月22日上午10:27:01
* version 0.0.1
*/
public interface UserService {
	TbUser getUserByToken(String token);
}
