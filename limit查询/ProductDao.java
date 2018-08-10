
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.utils.PageHibernateCallback;

public class ProductDao extends HibernateDaoSupport{

	// DAO层查询热门商品只显示10个
	/**
	 *   我们不能 向 之前 写 Dao 层那样的 ，直接用  limit 
	 *   因为如果我们底层切换了数据库，我们的代码就全完蛋了
	 *   我们依然 继承了  HibernateDaoSupport  ，就会有相应的模板
	 */
	public List<Product> findHot() {
		/*DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);     查询方法之一~~~~~~~~~~~~~~~~忘记了ORZ吧
		criteria.add(Restrictions.eq("is_hot", 1));									这里我们可以设置查询的条件
		List<Product> list = this.getHibernateTemplate().findByCriteria(criteria, 0, 10);*/ 	//  我们从 0 开始查 10  个
		
		// 我们上面的查询方法，如果查询条件多的话，就要有好多 add ，我们还有另外一种方法
		// 就是用 HibernateDaoSupport  的模板，但是这个要自己写回调，重新封装它的子类 
		// 我们写完的代码，会更长，但是我们找到了工具类，来实现我们的需求   PageHibernateCallback  （  Object[]{1}  可变素质男，用来存结果集    返回一个 List）
		List<Product> list = this.getHibernateTemplate().executeFind(new PageHibernateCallback<Product>("from Product where is_hot=?", new Object[]{1}, 0, 10));
		
		return list;
	}






	// DAO层的查询最新商品，这里我们也可以用会我们之前的那个方法，直接 add 条件，然后 find   ，但是，用到  limit 的，最好用这个工具类
	public List<Product> findNew() {
		List<Product> list = this.getHibernateTemplate().executeFind(new PageHibernateCallback<Product>("from Product order by pdate desc", null , 0, 10));
		return list;
	}

	// 统计某个分类下的商品的总数:
	public Integer findCountByCid(Integer cid) {
		//String hql = "select count(*) from Product p , CategorySecond cs where p.categorySecond = cs and cs.category.cid = ?";
		String hql = "select count(*) from Product p join p.categorySecond cs join cs.category c where c.cid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql,cid);
		System.out.println("list:============="+list.get(0).intValue());
		return list.get(0).intValue();
	}

	public List<Product> findByPage(Integer cid, int begin, int limit) {
		// String hql = "select p from Product p ,CategorySecond cs where p.categorySecond = cs and cs.category.cid = ?";
		String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid = ?";
		List<Product> list = this.getHibernateTemplate().executeFind(new PageHibernateCallback<Product>(hql, new Object[]{cid}, begin, limit));
		return list;
	}

	

	public List<Product> findByPageCsid(Integer csid, int begin, int limit) {
		String hql = "select p from Product p join p.categorySecond cs where cs.csid = ?";
		List<Product> list = this.getHibernateTemplate().executeFind(new PageHibernateCallback<Product>(hql, new Object[]{csid}, begin, limit));
		return list;


	public List<Product> findByPage(int begin, int limit) {
		String hql = "from Product";
		List<Product> list = this.getHibernateTemplate().executeFind(new PageHibernateCallback<Product>(hql, null, begin, limit));
		if(list.size() > 0){
			return list;
		}
		return null;
	}

	

}
