package cn.itcast.hibernate3.demo1;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.itcast.utils.HibernateUtils;

/**
 * Hibernate的测试类       区分持久化对象的三种状态
 * @author 姜涛
 *
 */
public class HibernateTest1 {
	/**
	 * 	1.2.4三种状态对象转换:
	 *	瞬时态:
	 *		获得:
	 *			Book book = new Book();
     *
	 *		瞬时--->持久
	 *			* save(book);
	 *			* save()/saveOrUpdate();
	 *		瞬时--->脱管
	 *			* book.setId(1);
	 *	持久态:
	 *		获得:
	 *			Book book = (Book)session.get(Book.class,1);
	 *			* get()/load()/find()/iterate();
     *
	 * 		持久--->瞬时:
	 *			* delete(book);
	 *			* 非常特殊的状态:删除态.(被删除的对象,不建议去使用.)
	 *
	 *		持久--->脱管:
	 *			* session.close();
	 *			* close()/clear()/evict();
	 *	脱管态:
	 *		获得:
	 *			Book book = new Book();
	 *  		book.setId(1);
	 *	
	 *		脱管--->持久:
	 *			* session.update();
	 *			* update()/saveOrUpdate()/lock()
	 *
	 *		脱管--->瞬时:
	 *			* book.setId(null);
	 * 
	 * 
	 */
	@Test
	// 区分持久化对象的三种状态:
	public void demo1(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		// 向数据库中保存一本图书:
		Book book = new Book();	// 瞬时态:  一new出来，就有一个持久化对象了   没有唯一标识OID,没有与session关联.
		book.setName("Hiernate开发");
		book.setAuthor("孙XX");
		book.setPrice(65d);
		
		session.save(book); // 持久态:有唯一标识OID,与session关联.
		
		// 3.事务提交
		tx.commit();
		// 4.释放资源
		session.close();
		
		book.setName("Struts2开发"); // 脱管态:有唯一的标识,没有与session关联.  因为 session已经结束了
	}
	
	
	/**
	 * 1.2.5持久态对象有自动更新数据库的能力;!!
	 * 为什么可以做到自动更新，这就是要依靠到		Hibernate 框架的  一级缓存
	 * ****** 自动更新数据库的能力依赖了Hibernate的一级缓存.
	 */
	
	@Test
	// 测试持久态的对象自动更新数据库
	public void demo2(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		// 获得一个持久态的对象.
		Book book = (Book) session.get(Book.class, 1);			// 这个就是持久态的对象
		book.setName("Struts2开发");
		
		// session.update(book);   //我们原来做修改的时候，我们还得自己手动update才能修改，但是，我们框架会自动执行
		
		// 3.提交事务
		tx.commit();
		// 4.关闭资源
		session.close();
	}


	
	
	
	

	
	
}
