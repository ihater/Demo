package cn.itcast.spring3.demo1;

import org.junit.Test;

public class SpringTest1 {
	
	// 没有代理 代理对象的时候
	@Test
	public void demo1(){
		UserDao userDao = new UserDaoImpl();
		userDao.add();
		userDao.update();
	}
	
	/**
	 *  如果我们有了 代理对象，我们可以这么搞
	 */
	@Test
	public void demo2(){
		// 被代理对象
		UserDao userDao = new UserDaoImpl();
		// 创建代理对象的时候传入被代理对象.
		UserDao proxy = new JDKProxy(userDao).createProxy();
		// 调用我们的代理对象
		proxy.add();
		proxy.update();
	}
}
