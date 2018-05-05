// ����ҳ�����ʾ��Ʒ�ķ��� ������������������������������������
	public PageBean<Product> findByPage(Integer cid, Integer page) {
		int limit = 12; // ÿҳ��ʾ��¼��.
		int totalPage = 0; // ��ҳ��
		PageBean<Product> pageBean = new PageBean<Product>();
		pageBean.setPage(page);
		pageBean.setLimit(limit);
		// ��ѯ�ܼ�¼��:
		Integer totalCount = productDao.findCountByCid(cid);
		
		pageBean.setTotalCount(totalCount);
		
		// ��ҳ���ķ�װ
		if(totalCount % limit == 0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// ��Ʒ���ݼ���:
		int begin = (page - 1) * limit;
		List<Product> list = productDao.findByPage(cid,begin ,limit);
		pageBean.setList(list);
		return pageBean;
	}