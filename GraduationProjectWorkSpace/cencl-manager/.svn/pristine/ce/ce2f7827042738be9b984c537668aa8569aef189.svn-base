package com.cencl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cencl.common.pojo.EUTreeNode;
import com.cencl.mapper.TbItemCatMapper;
import com.cencl.pojo.TbItemCat;
import com.cencl.pojo.TbItemCatExample;
import com.cencl.pojo.TbItemCatExample.Criteria;
import com.cencl.service.ItemCatService;

/*
 * 商品分类树形列表管理
 * 		接收parentId参数，根据parentId查询子类目类别,返回一个分类列表
*/
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EUTreeNode> getCatList(long parentId) {
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		// 根据条件查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);

		// 创建TreeNodeList
		List<EUTreeNode> resultList = new ArrayList<>();

		// 把列表转换成TreeNodeList
		for (TbItemCat tbItemCat : list) {
			EUTreeNode euTreeNode = new EUTreeNode();
			euTreeNode.setId(tbItemCat.getId());
			euTreeNode.setText(tbItemCat.getName());
			// 如果是父节点就是closed，否则open
			euTreeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
			resultList.add(euTreeNode);
		}

		// 返回结果
		return resultList;
	}

}
