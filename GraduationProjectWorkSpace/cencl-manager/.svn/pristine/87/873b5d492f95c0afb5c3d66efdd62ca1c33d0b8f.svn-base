package com.cencl.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cencl.common.pojo.EUDataGridResult;
import com.cencl.common.utils.CenclResult;
import com.cencl.common.utils.HttpClientUtil;
import com.cencl.mapper.TbContentMapper;
import com.cencl.pojo.TbContent;
import com.cencl.pojo.TbContentExample;
import com.cencl.pojo.TbContentExample.Criteria;
import com.cencl.service.ContentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//接收content表中对应的pojo对象，把pojo对象插入到content表中,返回CenclResult
@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private TbContentMapper contentMapper;
	
	@Value("{REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("{REST_CONTENT_SYSNC_URL}")
	private String REST_CONTENT_SYSNC_URL;
	
	@Override
	public CenclResult insertContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		
		contentMapper.insert(tbContent);
		try{
			//添加缓存业务逻辑
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYSNC_URL + tbContent.getCategoryId());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return CenclResult.ok();
	}
	
	//根据id删除内容记录
	@Override
	public CenclResult deleteItem(Long id) {
		contentMapper.deleteByPrimaryKey(id);
		
		return CenclResult.ok();
	}
	
	
	@Override
	public CenclResult editContent(TbContent tbContent) {
		tbContent.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(tbContent);
		
		return CenclResult.ok();
	}
	
	@Override
	public EUDataGridResult getItemList(Integer page, Integer rows, Long categoryId) {
		//创建TbContentExample对象
		TbContentExample example = new TbContentExample();
		
		if(categoryId != null){
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
			
		}
		
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		//创建返回对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		
		//取记录总条数
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}


}
