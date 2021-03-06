package com.cencl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.HttpClientUtil;
import com.cencl.pojo.TbItem;
import com.cencl.service.ItemService;

//商品管理controller
@Controller
public class ItemController {
	@Value("${SEARCH_BASE_URL}")
	private String  SEARCH_BASE_URL;
	@Value("${SEARCH_ADD_URL}")
	private String  SEARCH_ADD_URL;
	@Value("${SEARCH_DEL_URL}")
	private String  SEARCH_DEL_URL;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYSNC_URL}")
	private String REST_CONTENT_SYSNC_URL;
	
	@Autowired
	private ItemService itemService;
	
	//URL与配置文件的关系
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	//使用POJO接收表单中的内容(pojo中的属性要和表单中的name一致)，调用service，
	//返回CenclResult对象，返回JSON格式数据
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	private CenclResult createItem(TbItem item, String desc, String itemParams) throws Exception {
		CenclResult result = itemService.createItem(item, desc, itemParams);
		return result;
	}
	
	
	//根据商品id删除商品
	@RequestMapping("/item/deleted")
	@ResponseBody
	public CenclResult deleteItem(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String param = request.getParameter("ids");
		String[] ids = param.split(",");
		for(int i=0; i<ids.length; i++){
			Long id = Long.parseLong(ids[i]);
			itemService.deleteItem(id);
			//HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYSNC_URL + id);
		}
		
		return CenclResult.ok();
	}
		
	@RequestMapping(value="/item/update",method=RequestMethod.POST)
	@ResponseBody
	public CenclResult updateItem(TbItem item, String desc,String itemParams) {
		CenclResult result=itemService.updateItem(item, desc,itemParams);
		TbItem	tbitem=(TbItem) result.getData();
		String id=tbitem.getId().toString();
		return result;
	}
	
}
