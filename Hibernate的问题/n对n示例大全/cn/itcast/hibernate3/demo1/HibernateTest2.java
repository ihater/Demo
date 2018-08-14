package cn.itcast.hibernate3.demo1;
/**
 * Hibernate 的一级缓存
 */
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.itcast.utils.HibernateUtils;

public class HibernateTest2 {
	/**
	1.3.1Hibernate的一级缓存:
		什么是缓存:
		* 缓存将数据库/硬盘上文件中数据,放入到缓存中(就是内存中一块空间).当再次使用的使用,可以直接从内存中获取.

		缓存的好处:
		* 提升程序运行的效率.缓存技术是Hibernate的一个优化的手段.

		Hibernate分成两个基本的缓存:
		* 一级缓存:Session级别的缓存.一级缓存与session的生命周期一致.自带的.不可卸载.
		* 二级缓存:SessionFactory级别的缓存.不是自带的.

		在 Session 接口的实现中包含一系列的 Java 集合, 这些 Java 集合构成了 Session 缓存. 只要 Session 实例没有结束生命周期, 存放在它缓存中的对象也不会结束生命周期.
	*/
	
	@Test
	// 证明一级缓存的存在
	public void demo3(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();		
		
		// save方法可以向一级缓存中存放数据的.
		/*Book book = new Book();
		book.setName("JQuery开发");
		book.setAuthor("张XX");
		book.setPrice(45d);
		
		Integer id = (Integer) session.save(book);      // 我们save 的时候，Hibernate 就会去执行 保存操作，并且返回的是一个 id
		
		Book book2 = (Book) session.get(Book.class, id); 	// 这里我们session  还没关闭，这个id的数据就还在缓存里面，这里 get 不会触发SQL语句，直接在缓存里查
		
		System.out.println(book2);*/
		
		// 分别用get执行两次查询.获取第一条数据
		Book book1 = (Book) session.get(Book.class, 1);// 马上发生SQL去查询
		System.out.println(book1);
		
		Book book2 = (Book) session.get(Book.class, 1);// 不发生SQL,因为使用一级缓存的数据
		System.out.println(book2);
		
		// 3.提交事务
		tx.commit();
		// 4.关闭资源
		session.close();
	}
	
	
	/**
	 *  深入理解一级缓存的结构：快照区------
	 *  我们在执行 ：
	 *  Book book = new Book();
		book.setName("JQuery开发");
		book.setAuthor("张XX");
		book.setPrice(45d);
	 *  的时候，这些数据会被存放到我们的      actionmQueue  的活动队列中，就是一些变量名  --- 值  的键值对
	 *  除了这个以外，还有一个   persistenceContext 的一级缓存区 
	 *  里面是一个  map 集合，map的                                key 是一级缓存区域                                   value 是指 快照区域（快照数据 loadeState ）
	 *  一开始执行查询的时候，我们的book对象（ id，name，price）都会存在 key
	 *  同时，也会把这样的数据拷贝一份在 value 的 loadeState 快照区
	 *  
	 *  
	 *  我们在执行 set 更新数据的时候 ，我们会改变 一级缓存的时候，但是我们的快照区的数据不会变
	 *  我们在  执行      3.提交事务  tx.commit();  的时候，会先比较 一级缓存跟快照区的时候，是否一致
	 *  如果一直，就不更新，不一致就更新
	 */
	@Test
	// 深入理解一级缓存结构:快照区:
	public void demo4(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		// 获得一个持久态的对象.
		Book book = (Book) session.get(Book.class, 1);   // 向一级缓存存数据，也拷贝一份数据到快照区
		book.setName("Spring3开发");						//  改变一级缓存的数据
		
		// 3.提交事务
		tx.commit();									// 一提交，会先比对一级缓存跟快照区的数据
		// 4.关闭资源
		session.close();
	}
	
	/**
	 *  Hibernate有一些方法，用来管理我们的一级缓存
	 */
	@Test
	// 一级缓存的管理:clear/evict();
	public void demo5(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		Book book1 = (Book) session.get(Book.class, 1);
		Book book2 = (Book) session.get(Book.class, 2);
		System.out.println(book1);
		System.out.println(book2);							//这里执行，两个都会执行　sql 语句，只有执行了，才会把返回的东西存在缓存
		
		// session.clear();// 清空一级缓存的区域.
		session.evict(book1); // 清空一级缓存的某个对象.		// 这里就清除了  book1 的缓存
		
		Book book3 = (Book) session.get(Book.class, 1);
		Book book4 = (Book) session.get(Book.class, 2);
		System.out.println(book3);
		System.out.println(book4);
		// 3.提交事务
		tx.commit();
		// 4.关闭资源
		session.close();
	}

	/**
	 *	刷出缓存，我们一般在调用返回对象的时候，才刷出 sql语句
	 *  但是  flush 可以将 缓存区 中的sql 语句全部查询出来 
	 */
	@Test
	// 一级缓存的管理:flush()刷出缓存.
	public void demo6(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		Book book = (Book) session.get(Book.class, 1);
		book.setName("Hibernate3开发");
		
		session.flush();// 发出update语句.，什么是刷出，就是把缓存区的sql 语句发送出来
		
		// 3.提交事务
		tx.commit();
		// 4.关闭资源
		session.close();
	}
	
	
	/**
	 *  refresh ： 将快照区的数据铺盖到  一级缓存中去
	 *  这样我们 在 一级缓存中修改的数据就会被还原
	 */
	@Test
	// 一级缓存的管理:refresh():将快照区的数据,覆盖了一级缓存的数据.
	public void demo7(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// 2.开启事务
		Transaction tx = session.beginTransaction();	
		
		Book book = (Book) session.get(Book.class, 1);
		book.setName("PHP开发");							// 在这里我们就修改了一级缓存中的数据，就跟快照区的数据不一致了
		
		session.refresh(book);						// 我们又把修改了一级缓存的数据改回来了， 我们的数据库又保持一致了
		
		// 3.提交事务
		tx.commit();
		// 4.关闭资源
		session.close();
	}
	
	
	/**
	 *  控制一级缓存刷出的时机（我们一般都是在使用这个对象的时候，刷出 sql）
	 *  	FlushMode:
	 		* 常量:
	 			* ALWAYS		:每次查询的时候都会刷出.手动调用flush.事务提交的时候.
	 			* AUTO		:默认值.有些查询会刷出.手动调用flush.事务提交的时候.
	 			* COMMIT		:在事务提交的时候,手动调用flush的时候.
	 			* MANUAL		:只有在手动调用flush才会刷出.

			严格程度:MANUAL > COMMIT > AUTO > ALWAYS
	 *
	 *  
	 */
	@Test
	// 一级缓存的刷出的时机(了解)
	public void demo8(){
		// 1.创建Session
		Session session = HibernateUtils.openSession();
		// session.setFlushMode(FlushMode.COMMIT);// 事务提交,及手动调用flush.
		session.setFlushMode(FlushMode.MANUAL);// 只有手动调用flush的时候才会发送
		// 2.开启事务
		Transaction tx = session.beginTransaction();
		
		Book book = (Book) session.get(Book.class, 1);
		book.setName("JavaScript开发");
		
		// List<Book> list = session.createQuery("from Book").list();
		// System.out.println(list);
		// Book book2 = (Book) session.get(Book.class, 1); // 不发送SQL,从一级缓存中获取数据.
		
		
		// 3.提交事务
		tx.commit();
		
		session.flush();
		// 4.关闭资源
		session.close();
	}
	
	

}
