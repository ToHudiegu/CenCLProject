package com.cencl.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.mapper.TbItemParamMapper;
import com.cencl.pojo.TbContent;
import com.cencl.pojo.TbContentExample;
import com.cencl.pojo.TbItemParam;
import com.cencl.pojo.TbItemParamExample;
import com.cencl.pojo.TbItemParamExample.Criteria;
import com.cencl.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//商品规格参数模板
//接收商品分类id，调用mapper查询规格表，返回CenclResult
@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Autowired
	private TbItemParamMapper tbItemParamMapper;	
	
	@Override
	public CenclResult getItemParamById(long id) {
		TbItemParamExample example = new TbItemParamExample();
		//创建查询条件
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(id);
		//包含大文本列（规格参数模板）
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		
		if (list != null && list.size() > 0) {
			return CenclResult.ok(list.get(0));
		}
		
		return CenclResult.ok();
	}


	@Override
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		TbItemParamExample example = new TbItemParamExample();
		
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		
		//返回对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		
		//取记录总条数
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

	@Override
	public CenclResult deleteItemParam(Long cid) {
		tbItemParamMapper.deleteByPrimaryKey(cid);
		
		return CenclResult.ok();
	}


	@Override
	public CenclResult insertItemParam(TbItemParam tbItemParam) {
		//补全pojo
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		//插入到规格参数模板表
		tbItemParamMapper.insert(tbItemParam);
		return CenclResult.ok();
	}
	

}
