package cn.itcast.spring3.demo2;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * 使用CGLib生成代理对象
 * 生成代理的原理  ：  CGLIB生成代理机制:其实生成了一个真实对象的子类.   
 * CGLib 对任何一个类都可以生成代理，不用像 JDK 一样 ，要对继承一个接口的类进行增强
 * 		下载cglib的jar包.
 * 			现在做cglib的开发,可以不用直接引入cglib的包.     已经在spring的核心中集成cglib.
 *
 */
public class CGLibProxy implements MethodInterceptor{
	private ProductDao productDao;

	public CGLibProxy(ProductDao productDao) {
		super();
		this.productDao = productDao;
	}
	
	public ProductDao createProxy(){
		// 使用CGLIB生成代理:
		
		// 1.创建核心类:
		Enhancer enhancer = new Enhancer();
		
		// 2.为其设置父类:
		// 因为这里做动态代理做增强的方式是生成父类，所以我们要知道哪个是父类
		enhancer.setSuperclass(productDao.getClass());
		// 3.设置回调:就像我们JDK动态代理的 invoke一样
		enhancer.setCallback(this);
		// 4.创建代理:
		return (ProductDao) enhancer.create();
	}

	
	// 继承接口要重写的方法，就像我们的 JDK 的invoke 一样
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable { 
		
		// 创建增强
		if("add".equals(method.getName())){
			System.out.println("日志记录==============");
			Object obj = methodProxy.invokeSuper(proxy, args);
			return obj;
		}
		/**
		 *  注意！！！！！底层会自动切换
		 *  结论:Spring框架,如果类实现了接口,就使用JDK的动态代理生成代理对象,
		 *  	 如果这个类没有实现任何接口,使用CGLIB生成代理对象.
		 */
		return methodProxy.invokeSuper(proxy, args);
		
	}
}
