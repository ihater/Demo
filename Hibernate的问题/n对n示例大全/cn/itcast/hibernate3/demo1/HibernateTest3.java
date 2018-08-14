package cn.itcast.hibernate3.demo1;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.itcast.utils.HibernateUtils;
/**
 *	  操作持久化对象的方法
 */
public class HibernateTest3 {

	@Test
	// save方法
	public void demo9(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		Book book = new Book();// 瞬时态对象
		book.setName("网页平面设计");
		book.setAuthor("王XX");
		book.setPrice(32d);
		
		session.save(book);// 持久态，我们这里一执行  save 返回的是一个 book 表的主键 id值
		
		// 3.提交事务
		tx.commit();
		// 4.关闭资源
		session.close();
	}
	
	@Test
	// update方法:更新一条记录.将脱管对象转成持久对象
	public void demo10(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		Book book = new Book();//瞬时态.
		book.setId(1);// 脱管态
		book.setName("MyBatis开发");
		
		session.update(book);// 持久态.
		
		// 3.提交事务
		tx.commit();
		// 4.关闭资源
		session.close();
	}
	
	
	/**
	 * 根据对象状态的不同执行不同的save获得update方法.
	 * 如果对象是一个瞬时态对象:执行save操作.
	 * 如果对象是一个脱管态对象:执行update操作.
	 * 设置id不存在,就会报错,可以在<id>上设置一个unsaved-value=”-1”,执行保存的操作.
	 */
	
	@Test
	// saveOrUpdate方法:保存或更新一条记录
	// 如果对象是瞬时,执行save操作,如果对象是脱管,执行update操作.
	// 如果设置的id数据库中没有保存的.可以在映射文件的<id>上设置unsaved-value="-1"执行保存操作.
	public void demo11(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		/*	
		 // 如果我们要向数据库中保存一本图书，我们就要先 new  出一个图书对象
		 // 然后将图书对象交给框架保存
	 	Book book = new Book();			//   瞬时态.
		book.setName("MySQL数据库");
		
		// <class name="cn.itcast.hibernate3.demo1.Book" table="book" select-before-update="true"> 
		如果我们在这里设置这个 ：  select-before-update="true" 
		我们在执行 save 之前，就会先查询，这样如果跟数据库一样的  save 就不会执行 sql
		
		session.saveOrUpdate(book);// 执行保存.
		*/		
		
		Book book = new Book();//瞬时态.
		// 
		book.setId(-1); // 脱管态:		如果这里不在 xml 文件中设置，就会报错，因为 id 是主键
		book.setName("Spring大全");
		
		session.saveOrUpdate(book);// 执行更新.
		// 3.提交事务
		tx.commit();
		// 4.关闭资源
		session.close();
	}
	
	

}
