package com.cencl.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cencl.portal.pojo.CartItem;

import com.cencl.common.utils.CenclResult;

/* *
* <p>Title: </p>
* <p>Description: </p>
* <p>Company: Nothing</p>
* @author:  phubing
* @date: 2017年12月22日上午11:04:48
* version 0.0.1
*/
public interface CartService {
	CenclResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);
	
	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);

	CenclResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
	
}
