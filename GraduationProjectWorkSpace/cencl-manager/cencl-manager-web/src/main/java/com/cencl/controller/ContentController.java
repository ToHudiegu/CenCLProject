package com.cencl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.HttpClientUtil;
import com.cencl.mapper.TbContentMapper;
import com.cencl.pojo.TbContent;
import com.cencl.service.ContentService;


//接收表单中的内容，使用pojo接收，要求pojo的属性和表单中的name一致
//调用service，返回CenclResult（返回的是JSON数据格式）
@Controller
@RequestMapping("/content")
public class ContentController {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYSNC_URL}")
	private String REST_CONTENT_SYSNC_URL;
	
	@Autowired
	private ContentService contentService;
	@Autowired
	private TbContentMapper contenMapper;
	
	@RequestMapping("/save")
	@ResponseBody
	public CenclResult insertContent(TbContent tbContent){
		CenclResult cenclResult = contentService.insertContent(tbContent);
		//同步缓存
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYSNC_URL + tbContent.getCategoryId());
		
		return cenclResult;
	}
	
	
	@RequestMapping("/delete")
	public CenclResult deleteContent(HttpServletRequest request, RedirectAttributes redirectAttributes){
		String parameter = request.getParameter("ids");
		String[] ids = parameter.split(",");
		for(int i=0; i<ids.length; i++){
			Long id = Long.parseLong(ids[i]);
			contentService.deleteItem(id);
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYSNC_URL + id);
		}
		
		return CenclResult.ok();
	}
	
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getItemList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Long categoryId){
		EUDataGridResult result = contentService.getItemList(page, rows, categoryId);
		
		return result;
	}
	
	
	@RequestMapping("/edit")
	@ResponseBody
	public CenclResult editContent(TbContent tbContent){
		CenclResult result = contentService.editContent(tbContent);
		
		//同步缓存
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYSNC_URL + tbContent.getCategoryId());
		
		return CenclResult.ok();
	}
	
}
