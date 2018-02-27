package com.cencl.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.JsonUtils;
import com.cencl.mapper.TbItemDescMapper;
import com.cencl.mapper.TbItemMapper;
import com.cencl.mapper.TbItemParamItemMapper;
import com.cencl.pojo.TbItem;
import com.cencl.pojo.TbItemDesc;
import com.cencl.pojo.TbItemParam;
import com.cencl.pojo.TbItemParamItem;
import com.cencl.pojo.TbItemParamItemExample;
import com.cencl.pojo.TbItemParamItemExample.Criteria;
import com.cencl.rest.dao.JedisClient;
import com.cencl.rest.service.ItemService;

//接收商品id，根据商品id查询商品基本信息，返回一个商品的pojo，使用CenclResult包装返回
@Service
public class ItemServiceImpl implements ItemService{
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public CenclResult getItemBaseInfo(long itemId) {
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return CenclResult.ok(item);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//根据商品id查询商品信息
				TbItem item = itemMapper.selectByPrimaryKey(itemId);
				//使用TaotaoResult包装一下
				try {
					//把商品信息写入缓存
					jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
					//设置key的有效期
					jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return CenclResult.ok(item);
			}
	
	//接收商品id根据商品id查询商品描述。返回商品描述的pojo。使用TaotaoResult包装。需要添加缓存逻辑。
	@Override
	public CenclResult getItemDesc(long itemId) {
		//添加缓存
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return CenclResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//创建查询条件
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		
		try {
			//把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			//设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return CenclResult.ok(itemDesc);
	}

	//接收商品id，取出商品的规格参数信息，返回pojo使用CenclResult包装
	@Override
	public CenclResult getItemParam(long itemId) {
		//添加缓存
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return CenclResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询规格参数
		//设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size()>0) {
			TbItemParamItem paramItem = list.get(0);
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return CenclResult.ok(paramItem);
		}
		return CenclResult.build(400, "无此商品规格");
	}
	
	
	

}