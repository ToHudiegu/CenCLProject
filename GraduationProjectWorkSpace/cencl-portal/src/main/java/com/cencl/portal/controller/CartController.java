package com.cencl.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.CookieUtils;
import com.cencl.common.utils.JsonUtils;
import com.cencl.pojo.TbItem;
import com.cencl.portal.pojo.CartItem;
import com.cencl.portal.service.CartService;
import com.cencl.portal.service.impl.CartServiceImpl;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, 
			@RequestParam(defaultValue="1")Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		CenclResult result = cartService.addCartItem(itemId, num, request, response);
		return "cartSuccess";
	}
	/*
	@RequestMapping("/success")
	public String showSuccess(){
		return "cartSuccess";
	}*/
	
	//把商品列表传递给展示页面
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response,Model model){
		List<CartItem> itemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList",itemList);
		
		return "cart";
	}
	
	//更新购物车数量
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public CenclResult updateNum(@PathVariable Long itemId,@PathVariable  Integer num,HttpServletRequest request, HttpServletResponse response){
		//取购物车列表
		List<CartItem> itemList = getCartItemList(request);
		
		//遍历商品列表查找该商品
		for (CartItem cartItem : itemList) {
			if(cartItem.getId() == itemId.longValue()){
				cartItem.setNum(num);
				break;
			}
		}
		
		//写入cookie
		CookieUtils.setCookie(request, response, "TT_TOKEN", JsonUtils.objectToJson(itemList), true);
		
		
		// 返回结果,true);
		return CenclResult.ok();
	}
	
	/**
	 * 从cookie中取商品列表
	 * <p>Title: getCartItemList</p>
	 * <p>Description: </p>
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		//从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (cartJson == null) {
			return new ArrayList<>();
		}
		//把json转换成商品列表
		try {
			List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
		
	}
	
	
}
