package com.lzx.service;

import com.lzx.common.pojo.EasyUIDataGridResult;
import com.lzx.pojo.TbItem;

public interface ItemService {

//	根据商品ID查询商品，接口的实现类在manager_service 的 impl 接口实现
	TbItem getItemById(long itemid);
	
	EasyUIDataGridResult getItemList(int page, int rows);
}
