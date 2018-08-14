package cn.itcast.hibernate3.demo2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.itcast.utils.HibernateUtils;

public class HibernateTest2 {
	
	/**
	 *  删除的级联
	 */

	@Test
	// 测试对象的导航关系:				看第二天的视频  11 集 20分     两边都有自动更新
	// 这个看到底有 几条 sql 语句，可以把导航图会出来，然后看方向，能一直通的就是了
	public void demo5(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 定义一个客户:
		Customer customer = new Customer();
		customer.setCname("金刚");
		
		// 定义三个订单
		Order order1 = new Order();
		order1.setAddr("西三旗");
		
		Order order2 = new Order();
		order2.setAddr("上地");
		
		Order order3 = new Order();
		order3.setAddr("五道口");
		
		order1.setCustomer(customer);			// 一个订单关联到一个客户		订单---> 客户
		
		customer.getOrders().add(order2);		//  一个客户关联了两个订单		客户 ---> 订单
		customer.getOrders().add(order3);
		
		// session.save(order1); // 共发送4条insert语句:
		// session.save(customer);// 共发送3条insert语句:
		session.save(order2);	// 发送一条 insert 语句
		
		tx.commit();
		session.close();
	}
	
	/**
	 *  用级联删除    删除一个客户:    默认情况下 ：  是先将外键置为null, 再删除数据记录.
	 *  置为  null 了但是，订单的记录还是在数据库里面的
	 */
	@Test
	// 删除一个客户:
	// 默认的情况下,   是先将外键置为null, 再删除数据记录.
	public void demo6(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 删除的时候有两种:
		// 先查询在删除的情况:
		Customer customer = (Customer) session.get(Customer.class, 1);
		session.delete(customer);
		
		tx.commit();
		session.close();
	}
	
	@Test
	// 级联删除   :    删除客户的时候级联删除订单.
	// 在Customer.hbm.xml的<set>标签上配置cascade="delete"
	//   cascade="save-update,delete"    设置级联shanchu
	public void demo7(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 级联删除:先查询,在删除的方式.
		Customer custoemr = (Customer) session.get(Customer.class, 1);
		session.delete(custoemr);
		
		tx.commit();
		session.close();
	}
	
	/**
	 *  我们删除 订单的时候，级联删除客户
	 *  问题： 如果客户有两个订单，会在怎么样？
	 *  	如果两端都有设置级联删除的话，我们删除一个订单，客户会被删除，这个客户对应的其他记录也会被删除
	 *  	如果我们的客户表 没有设置级联删除， 我们删除一条记录，客户被删除，客户的其他记录的外键，就会变成  null
	 */
	@Test
	// 级联删除:删除订单的时候,级联删除客户.
	public void demo8(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		Order order = (Order) session.get(Order.class, 1);
		session.delete(order);
		
		tx.commit();
		session.close();
	}
	

}
