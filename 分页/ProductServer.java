// 分类页面的显示商品的方法 ！！！！！！！！！！！！！！！！！！
	public PageBean<Product> findByPage(Integer cid, Integer page) {
		int limit = 12; // 每页显示记录数.
		int totalPage = 0; // 总页数
		PageBean<Product> pageBean = new PageBean<Product>();
		pageBean.setPage(page);
		pageBean.setLimit(limit);
		// 查询总记录数:
		Integer totalCount = productDao.findCountByCid(cid);
		
		pageBean.setTotalCount(totalCount);
		
		// 总页数的封装
		if(totalCount % limit == 0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 商品数据集合:
		int begin = (page - 1) * limit;
		List<Product> list = productDao.findByPage(cid,begin ,limit);
		pageBean.setList(list);
		return pageBean;
	}