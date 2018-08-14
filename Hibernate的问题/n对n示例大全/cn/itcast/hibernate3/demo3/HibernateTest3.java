package cn.itcast.hibernate3.demo3;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.itcast.utils.HibernateUtils;

/**
 * 多对多的测试类:
 * 
 * @author 姜涛
 * 
 */

public class HibernateTest3 {
	
	
	/**
	 *  我们多对多的操作！！！！一定要有一方要放弃对数据库的更新的权利，就是放弃更新  sql   inverse="true"
	 */
	@Test
	// 保存学生和课程.为学生选择一些课程:
	public void demo1() {
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();

		// 创建学生:
		Student student1 = new Student();			// 创建 学生
		student1.setSname("张三");
		Student student2 = new Student();			// 创建 学生
		student2.setSname("李四");

		// 创建课程:
		Course course1 = new Course();				// 创建课程
		course1.setCname("Java语言");
		Course course2 = new Course();				// 创建课程
		course2.setCname("Android语言");

		// 张三选1号和2号课
		student1.getCourses().add(course1);			// 学生选课
		student1.getCourses().add(course2);

		course1.getStudents().add(student1);		//课　隶属于　某个学生
		course2.getStudents().add(student1);

		student2.getCourses().add(course1);			// 二号学生选课
		course1.getStudents().add(student2);		// 二号学生选的课

		// 执行保存:
		session.save(student1);
		session.save(student2);
		session.save(course1);
		session.save(course2);

		tx.commit();
		session.close();
	}
	
	/**
	 *  多对多的级联操作     保存学生关联课程
	 */
	@Test
	// 级联操作:级联保存:保存学生关联课程
	// 在Student.hbm.xml中配置<set>上 cascade="save-update"
	public void demo2() {
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();

		// 创建学生:
		Student student1 = new Student();		// 创建学生
		student1.setSname("王五");

		// 创建课程:
		Course course1 = new Course();			// 创建课程
		course1.setCname("PHP语言");
		
		student1.getCourses().add(course1);		// 两方添加
		course1.getStudents().add(student1);
		
		session.save(student1);					// 单项保存

		tx.commit();
		session.close();
	}
	
	
	
	@Test
	// 级联删除:在多对多中很少使用.
	// 删除:删除学生同时删除学生关联选课
	public void demo3(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, 3);
		session.delete(student);
		
		tx.commit();
		session.close();
	}
	
	

	
	@Test
	// 多对多的学生退选.
	public void demo4(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		
		// 查询一号学生
		Student student = (Student) session.get(Student.class, 1);
		Course course = (Course) session.get(Course.class, 2);
		student.getCourses().remove(course);
		
		tx.commit();
		session.close();
	}
	
	

}
