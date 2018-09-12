package cn.itcast.spring3.demo4;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest4 {

	/**
	 *	Bean的生命周期:
	 *	配置Bean的初始化和销毁的方法:
	 *		配置初始化和销毁的方法:
	 * 			init-method=”setup”			   Product p1 = (Product) applicationContext.getBean("product");
	 * 			destroy-method=”teardown”
	 *	执行销毁的时候,必须手动关闭工厂,而且只对scope=”singleton”有效.
 	 *
	 *	Bean的生命周期的11个步骤:
	 *		1.instantiate bean对象实例化
	 *		2.populate properties 封装属性
	 *		3.如果Bean实现BeanNameAware 执行 setBeanName
	 *		4.如果Bean实现BeanFactoryAware 或者 ApplicationContextAware 设置工厂 setBeanFactory 或者上下文对象 setApplicationContext
	 *	  * 5.如果存在类实现 BeanPostProcessor（后处理Bean） ，执行postProcessBeforeInitialization
	 *		6.如果Bean实现InitializingBean 执行 afterPropertiesSet 
	 *		7.调用<bean init-method="init"> 指定初始化方法 init
	 *	  *	8.如果存在类实现 BeanPostProcessor（处理Bean） ，执行postProcessAfterInitialization
	 *		9.执行业务处理
	 *		10.如果Bean实现 DisposableBean 执行 destroy
	 *		11.调用<bean destroy-method="customerDestroy"> 指定销毁方法 customerDestroy
     *
	 *	在CustomerService类的add方法之前进行权限校验? 
	 * 
	 *  5、8、1、2、3、4、5、6、7、8 （执行CustomerServiceImpl的方法） 10、11
	 *  因为第五步 和第八步先执行，所以我们可以去做一些增强的操作
	 */
	@Test
	// Bean完整的生命周期
	public void demo1() {
		/**
		 *  为什么要用 ClassPathXmlApplicationContext  ，是因为这个有一个 close 的 方法
		 */
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		CustomerService customerService = (CustomerService) applicationContext.getBean("customerService");
		customerService.add();
		customerService.find();
		
		applicationContext.close();
	}
}
