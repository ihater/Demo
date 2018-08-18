package com.lzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzx.common.pojo.EasyUIDataGridResult;
import com.lzx.pojo.TbItem;
import com.lzx.service.ItemService;



/**
 * 商品管理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;	// 因为我们dubbo引入了服务.
	
	@RequestMapping("/item/{itemId}")	//restful 风格的URL，相当于PHP的路由
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		System.out.println("item success!!!!");
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
//		因为我们的EasyUI法需要的Json数据，需要total 字段，rows就是查询数据的list集合
//		itemService.getItemList 就是把查询到的分页数据，存到 EasyUIDataGridResult对象
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
}
