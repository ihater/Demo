package com.lzx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzx.common.pojo.EasyUIDataGridResult;
import com.lzx.mapper.TbItemMapper;
import com.lzx.pojo.TbItem;
import com.lzx.pojo.TbItemExample;
import com.lzx.service.ItemService;


@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	@Override
	public TbItem getItemById(long itemId) {
		// TODO Auto-generated method stub
		/**
		 * 我们在这里，定义了一个service，service，service
		 * 我们就要去发布服务了
		 * service继承接口，注入 Dao		
		 */
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取查询结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
	}
}
