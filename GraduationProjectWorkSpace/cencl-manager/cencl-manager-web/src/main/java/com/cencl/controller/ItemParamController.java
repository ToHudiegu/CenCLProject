package com.cencl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.pojo.TbItemParam;
import com.cencl.service.ItemParamService;

//接收id参数，调用service查询规格参数模板，返回cenclresult,返回JSON数据
//商品规格参数
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemcatid}")
	@ResponseBody
	public CenclResult getItemCatById(@PathVariable Long itemcatid){
		CenclResult result = itemParamService.getItemParamById(itemcatid);
		
		return result;
	}
	
	//接收cid、规格参数，创建TbItemParam对象，调用Service，返回CenclResult结果，返回一个JSON数据
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public CenclResult insertItemParam(@PathVariable Long cid, String paramData){
		TbItemParam tbItemParam = new TbItemParam();
		
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		
		CenclResult cenclResult = itemParamService.insertItemParam(tbItemParam);
		
		return cenclResult;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getItemList(@RequestParam Integer page, @RequestParam Integer rows){
		EUDataGridResult result = itemParamService.getItemList(page, rows);
		
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public CenclResult deleItemParam(HttpServletRequest request, RedirectAttributes redirectAttributes){
		String string = request.getParameter("ids");
		String[] ids = string.split(",");
		for(int i=0; i<ids.length; i++){
			Long id = Long.parseLong(ids[i]);
			itemParamService.deleteItemParam(id);
		}
		
		return CenclResult.ok();
	}
	
	
	
	
}
