package cn.itcast.spring3.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK的动态代理的机制
 * 
 */
public class JDKProxy implements InvocationHandler{
	private UserDao userDao;

	// 先有一个动态代理的对象，获取到我们要在增强的类对象
	public JDKProxy(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	// 创建一个代理对象
	public UserDao createProxy() {
		UserDao proxy = (UserDao) Proxy.newProxyInstance(userDao.getClass()
				.getClassLoader(), userDao.getClass().getInterfaces(), this);
		return proxy;
	}

	// 调用目标对象的任何一个方法 都相当于调用invoke();
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		if("add".equals(method.getName())){
			// 记录日志:
			System.out.println("日志记录=================");
			/**
			 *  我们对我们要增强的方法进行增强，当然还要继续执行我们原来的东西
			 */
			Object result = method.invoke(userDao, args);
			return result;
		}
		
		return method.invoke(userDao, args);
	}
}
