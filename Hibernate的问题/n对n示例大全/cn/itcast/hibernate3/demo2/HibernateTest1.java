package cn.itcast.hibernate3.demo2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.itcast.utils.HibernateUtils;

/**
 *	Hibernate 框架中的一对多的配置  （我们有 两个数据库，Customer Order）
 *	注意，我们的 客户  和订单 的关系。是一对多的关系
 *	注意！！！！！ 我们在学sql 的时候，是在多的一方的表中，加入一个 字段（另一张表的 主键）
 *	但是在我们面向对象的 编程里面，我们就要 将这个字段变成 客户这个 对象
 */
public class HibernateTest1 {
	
	/**
	 *  测试我们xml 配置 一对多 是否正确
	 */
	@Test
	// 向客户表插入一个客户,在订单表中插入两个订单.
	public void demo1(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 定义一个客户:
		Customer customer = new Customer();
		customer.setCname("郭浩");
		
		// 定义两个订单:
		Order order1 = new Order();
		order1.setAddr("西三旗中腾");
		
		Order order2 = new Order();
		order2.setAddr("西三旗金燕龙");
		
		// 建立关系:
		order1.setCustomer(customer);					// 重点，我们要在这里建立两个之间的关系
		order2.setCustomer(customer);					// 订单要  set 客户
		
		customer.getOrders().add(order1);				// 客户要 add 订单
		customer.getOrders().add(order2);
		
		
		session.save(customer);							// 两边都保存
		session.save(order1);
		session.save(order2);
		
		// 提交完成，简直美滋滋
		tx.commit();
		session.close();
	}
	
	@Test
	// 保存客户和订单的时候,是否可以只保存其中的一方?    因为我们上面两方都保存，可不可以只保存客户？
	// 不行的报一个异常:一个持久态对象关联一个瞬时的对象.-----------------------这是不允许的
	// 但是我们在一对一的表中，可以做到这个只保存一方，但是需要配置  级联报错
	//（就是告知框架，我们在操作当前对象的时候，我们的关联对象怎么处理）  配置级联，是有反向的
	/**
	 *  假如我们需要配置级联操作
	 *  	级联方向性:     		--------   保存谁，级联谁
	 * 保存客户的时候,选择级联订单.
	 * 保存订单的时候,选择级联客户.
	 */
	public void demo2(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 定义客户:
		Customer customer = new Customer();
		customer.setCname("金刚");
		
		// 定义订单:
		Order order = new Order();
		order.setAddr("五道口");
		order.setCustomer(customer);
		
		customer.getOrders().add(order);
		
		// 保存的时候只保存一方:
		session.save(customer);
		//  只保存一个  就会爆一个  瞬时对象的异常
		/**
		 *  就是说，我们持久态（客户，因为save了）的对象关联了一个瞬时态（订单）的对象
		 */
		tx.commit();
		session.close();
	}
	
	@Test
	// 保存客户  级联订单.
	// <set>集合是客户的关联订单对象的集合.所以在<set>上配置一个属性:cascade="save-update"
	public void demo3(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 定义客户:
		Customer customer = new Customer();
		customer.setCname("郭浩");
		
		// 定义订单:
		Order order = new Order();
		order.setAddr("西三旗中腾建华");
		order.setCustomer(customer);
		
		customer.getOrders().add(order);
		
		// 保存的时候只保存一方:
		session.save(customer);					// 我们这里想只保存 用户，我们就需要在 客户这个配置文件中配置
		// 在客户的配置中 <set name="orders" cascade="save-update" inverse="true">   保存 或者 更新 的时候，级联
		
		tx.commit();
		session.close();
	}
	
	@Test
	// 保存订单   级联客户.
	// 在Order.hbm.xml中<many-to-one>配置cascade属性:级联保存
	public void demo4(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 定义客户:
		Customer customer = new Customer();
		customer.setCname("郭浩");
		
		// 定义订单:
		Order order = new Order();
		order.setAddr("西三旗中腾建华");
		order.setCustomer(customer);
		
		customer.getOrders().add(order);
		
		// 保存的时候只保存一方:
		session.save(order);				
		
		tx.commit();
		session.close();
	}
	

	
	
	
}
