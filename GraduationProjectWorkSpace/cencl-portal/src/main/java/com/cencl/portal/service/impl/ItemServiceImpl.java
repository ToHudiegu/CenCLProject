package com.cencl.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.HttpClientUtil;
import com.cencl.common.utils.JsonUtils;
import com.cencl.pojo.TbItemDesc;
import com.cencl.pojo.TbItemParam;
import com.cencl.pojo.TbItemParamItem;
import com.cencl.portal.pojo.ItemInfo;
import com.cencl.portal.service.ItemService;

/* *
* <p>Title: 商品信息管理</p>
* <p>Description: 接收商品id，调用cencl-rest服务，查询商品基本信息，得到一个json字符串，
* 				    把json字符串转换成Java对象</p>
* <p>Company: Nothing</p>
* @author:  phubing
* @date: 2017年12月19日上午10:02:45
* version 0.0.1
*/
@Service
public class ItemServiceImpl implements ItemService{
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_RUL}")
	private String ITEM_PARAM_RUL;
	
	
	@Override
	public ItemInfo getItemById(Long itemId) {

		try {
			//调用rest的服务查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			//如果不为空
			if (!StringUtils.isBlank(json)) {
				CenclResult taotaoResult = CenclResult.formatToPojo(json, ItemInfo.class);
				//判断返回状态码
				if (taotaoResult.getStatus() == 200) {
					ItemInfo item = (ItemInfo) taotaoResult.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//异常则返回空
		return null;
	}

	@Override
	public String getItemDescById(Long itemId) {
		try {
			String jsonData = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			//转换成java对象
			CenclResult result = CenclResult.formatToPojo(jsonData, TbItemDesc.class);
			
			//判断是否成功
			if(result.getStatus() == 200){
				TbItemDesc tbItemDesc = (TbItemDesc) result.getData();
				
				//取商品描述信息
				String itemDesc = tbItemDesc.getItemDesc();
				
				return itemDesc;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}


	@Override
	public String getItemParam(Long itemId) {
		try{
			//调用rest的服务查询商品基本信息
			String string = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_RUL + itemId);
			
			//转换成java对象
			CenclResult result = CenclResult.formatToPojo(string, TbItemParamItem.class);
			
			//判断结果状态码
			if(result.getStatus() == 200){
				TbItemParamItem tbItemParamItem = (TbItemParamItem) result.getData();
				
				String  paramData = tbItemParamItem.getParamData();
				
				//根据java对象生成html片段
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				//得到html片段
				sb.append("    </tbody>\n");
				sb.append("</table>");
				//返回html片段
				return sb.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		//转换出错则返回空
		return null;
	}

}
