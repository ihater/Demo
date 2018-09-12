package cn.itcast.spring3.demo2;

import org.junit.Test;

public class SpringTest2 {
	
	// 没有做增强的时候
	@Test
	public void demo1(){
		ProductDao productDao = new ProductDao();
		productDao.add();
		productDao.update();
	}
	
	//做了CGLib 做代理对象增强的时候
	@Test
	public void demo2(){
		ProductDao productDao = new ProductDao();
		ProductDao proxy = new CGLibProxy(productDao).createProxy();
		proxy.add();
		proxy.update();
	}
}
