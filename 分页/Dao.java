	public List<Product> findByPage(Integer cid, int begin, int limit) {
		// String hql = "select p from Product p ,CategorySecond cs where p.categorySecond = cs and cs.category.cid = ?";
		String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid = ?";
		List<Product> list = this.getHibernateTemplate().executeFind(new PageHibernateCallback<Product>(hql, new Object[]{cid}, begin, limit));
		return list;
	}



		// 2.QBC进行分页:
		Criteria criteria = session.createCriteria(Order.class);
		criteria.setFirstResult(10);
		criteria.setMaxResults(10);
		List<Order> list = criteria.list();


		// 1.HQL进行分页:
		/*
		 * Query query = session.createQuery("from Order");    		// 这是返回一个 query 接口
		 * query.setFirstResult(10); 								//从第几条查询
		 * query.setMaxResults(10); 							   	// 每页多少条