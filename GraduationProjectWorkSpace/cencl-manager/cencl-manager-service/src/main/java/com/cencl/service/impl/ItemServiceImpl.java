package com.cencl.service.impl;

import java.util.Date;
import java.util.List;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.IDUtils;
import com.cencl.mapper.TbItemDescMapper;
import com.cencl.mapper.TbItemMapper;
import com.cencl.mapper.TbItemParamItemMapper;
import com.cencl.mapper.TbItemParamMapper;
import com.cencl.pojo.TbItem;
import com.cencl.pojo.TbItemDesc;
import com.cencl.pojo.TbItemDescExample;
import com.cencl.pojo.TbItemExample;
import com.cencl.pojo.TbItemExample.Criteria;
import com.cencl.pojo.TbItemParamItem;
import com.cencl.pojo.TbItemParamItemExample;
import com.cencl.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cencl.pojo.TbItemParamItemExample.*;

/*
 * 	商品管理
 * */
@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		//添加查询条件
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}
	
	//接收商品id，调用dao查询商品信息,返回商品pojo对象
	//商品列表查询
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public CenclResult createItem(TbItem item, String desc, String itemParam) throws Exception {
		//item补全
		//生成商品ID
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// '商品状态，1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		//添加商品描述信息
		CenclResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		//添加规格参数
		result = insertItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return CenclResult.ok();
	}
	
	//添加商品描述
	private CenclResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(itemDesc);
		return CenclResult.ok();
	}
	
	//添加规格参数
	private CenclResult insertItemParamItem(Long itemId, String itemParam) {
		//创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		//向表中插入数据
		tbItemParamItemMapper.insert(itemParamItem);
		
		return CenclResult.ok();
		
	}

	@Override
	public CenclResult updateItem(TbItem tbItem, String desc, String itemParams) {
		//商品状态(1-正常，2-下架，3-删除)
		tbItem.setStatus((byte)1);
		//创建时间和更新时间
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		//更新商品表
		itemMapper.updateByPrimaryKeySelective(tbItem);
		//商品描述
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(tbItem.getId());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setUpdated(new Date());
		
		//插入商品描述数据
		tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
		
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.cencl.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(tbItem.getId());
		
		List<TbItemParamItem> listItemParamItem = tbItemParamItemMapper.selectByExample(example);
		
		//判断是否为空
		if(listItemParamItem.size() > 0 && listItemParamItem != null){
			TbItemParamItem itemParamItemModel = listItemParamItem.get(0);
			TbItemParamItem tbItemParamItem = new TbItemParamItem();
			tbItemParamItem.setId(itemParamItemModel.getId());
			tbItemParamItem.setParamData(itemParams);
			tbItemParamItem.setUpdated(new Date());
			
			tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
		}
		
		return CenclResult.ok(tbItem);
		
	}
	
	
	//根据商品id删除商品
	@Override
	public CenclResult deleteItem(Long id) {
		itemMapper.deleteByPrimaryKey(id);
		
		return CenclResult.ok();
	}

	@Override
	public CenclResult getItem(Long id) {
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		return CenclResult.ok(tbItemDesc);
		
	}

	@Override
	public CenclResult getItemQueryParam(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.cencl.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		
		return CenclResult.ok(list);
	}

	@Override
	public String getItemParamHtml(Long itemId) {
		
		
		
		
		
		
		return null;
	}
	
	
}
