package cn.itcast.spring3.demo1;

/**
 *	JDK 要做动态代理 要怎么做？？
 *	比如，我们要对这个 Impl 进行增强，要怎么做 
 *
 */
public class UserDaoImpl implements UserDao {

	// 假设，我们要在 add 方法之前，执行记录日志
	public void add() {
		System.out.println("添加用户...");
	}

	public void update() {
		System.out.println("修改用户...");
	}

}
