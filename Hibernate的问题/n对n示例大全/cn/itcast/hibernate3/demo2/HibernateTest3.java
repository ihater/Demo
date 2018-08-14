package cn.itcast.hibernate3.demo2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.itcast.utils.HibernateUtils;

/**
 * 一对多的测试
 * @author 姜涛
 *
 */
public class HibernateTest3 {
	
	/**
	 * 	1.5.4Hibernate中级联删除的效果:
	 *		cascade=”delete”
	 *
	 *	1.5.5Hibernate中的级联取值:
	 *		none				:不使用级联
	 *		dave-update		:保存或更新的时候级联
	 *		delete			:删除的时候级联
	 *		all				:除了孤儿删除以外的所有级联.
	 *		delete-orphan	:孤儿删除(孤子删除).[ 就是将多的那张表的外键 是 null  就像孤儿一样]
	 * 			仅限于一对多.  只有一对多时候,  才有父子存在.  认为一的一方是父亲,   多的一方是子方.
	 * 			当一个客户与某个订单解除了关系.  将外键置为null.  订单没有了所属客户, 相当于一个孩子没有了父亲. 将这种记录就删除了.
	 *		all-delete-orphan	:包含了孤儿删除的所有的级联.
	 * 
	 * 
	 */

	@Test
	// 孤儿删除:
	// 在Customer.hbm.xml中<set>上配置cascade="delete-orhpan"
	public void demo9(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 让1号客户与1号订单解除关系:
		Customer customer = (Customer) session.get(Customer.class, 1);   // 首先我们要先查到 1号 客户
		
		Order order = (Order) session.get(Order.class, 1);				// 然后我们要找到 我们的 1号订单
		
		customer.getOrders().remove(order);						// 拿到客户的  订单  并解除  哪张订单的关系
		
		tx.commit();
		session.close();
	}
	
	
	/**
	 *  我们要把 二号订单，归属到  一号 客户那里去，就是修改外键的值
	 */
	@Test
	// 双向维护:自动更新数据库,产生多余的SQL.
	// 双方都有外键的维护能力.必须让其中一方放弃外键的维护权.(一般情况下都是一的放弃.)
	public void demo10(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		/**
		 *  为什么会出现 多余 的 sql  语句？
		 *  因为：我们在查找 对象的 时候，会把查到的 值，存放到我们的 一级缓存（缓存区  和   快照区） 中去
		 *  我们 session.get(Customer.class, 1);  在缓存区 和快照区有  数据  [  id=1  cname= 小泽     orders{1} ]
		 *  我们  session.get(Order.class, 2);	在缓存区  和 快照区 哟数据   [  id=1  addr =将军路   customer =2 ]
		 *  
		 *  我们执行修改的时候，会修改我们的缓存区的数据：
		 *  customer.getOrders().add(order);	  在缓存区的记录   [  id=1  cname= 小泽     orders{1、2} ]
		 *  order.setCustomer(customer);		 在缓存区的记录   [  id=1  addr =将军路   customer =1 ]
		 *  
		 *  我们事务一提交  tx.commit();  就会比较  缓存区 和快照区的 记录 
		 *  但是我们的 客户  和订单   两个记录，都会不一样
		 *  所以，我们的Hibernate 就会发送两条  sql  语句来修改   客户表的外键【因为我们一对多的关系中，是靠外键来联系的，外键只有一个，在多的那张表中】
		 *  所以我们的 订单表，就会 有两条  sql 语句，来修改这个外键
		 *  
		 *  ###### 解决，我们要有 一方，要放弃对外键的维护权利，一般都是我们的  1 的一方放弃
		 *  ###### 配置inverse=”true”:在那一端配置.那么那一端放弃了外键的维护权.
		 */
		Customer customer = (Customer) session.get(Customer.class, 1);		//  拿到 一号客户
		Order order = (Order) session.get(Order.class, 2);					// 拿到 二号订单
		
		customer.getOrders().add(order);									// 我们的 一号客户 添加 二号订单
		order.setCustomer(customer);										// 我们 二号订单，转移到 一号客户
		
		tx.commit();
		session.close();
	}
	
	
	
	/**
	 *  我们用久了，就会 分不清  cascade 和 inverse 
	 *  cascade:操作关联对象.
	 *	inverse:控制外键的维护.
	 */
	@Test
	// 区分cascade和inverse
	// 在Customer.hbm.xml中的<set>上配置 cascade="save-update" inverse="true"
	public void demo11(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		Customer customer = new Customer();
		customer.setCname("张三");
		
		Order order = new Order();
		order.setAddr("西三旗");
		
		customer.getOrders().add(order);
		// 客户是否存到数据库:存
		// 订单是否存到数据库:存 cascade="save-update".外键是null.
		session.save(customer);
		
		tx.commit();
		session.close();
	}
	


	

	
	

	
}
