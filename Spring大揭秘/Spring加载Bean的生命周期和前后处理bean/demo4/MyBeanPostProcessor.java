package cn.itcast.spring3.demo4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
/**
这个类MyBeanPostProcessor是Spring生命周期中的第五步和第八步，是在Bean初始化之前，和Bean初始化之后执行的东西
		<!--
		怎么在相应的Bean之前处理Bean，我们只要把这个类交给（告诉）Spring就好了
		Spring底层会判断 MyBeanPostProcessor  是否继承额 BeanPostProcessor
		就可以在Bean 初始化之前 ，初始化之后，对其操作了。
		而且，是所有的类，都会经历这个类（MyBeanPostProcessor）进行判断
		-->


		比如我们的需求是，要在创建 customerService 这个bean之前，对其进行增强（增加权限校验之类的。）
*/
public class MyBeanPostProcessor implements BeanPostProcessor{
	/**
	 * bean:实例对象
	 * beanName:在配置文件中配置的类的标识.
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("第五步:初始化之前执行...");
		return bean;
	}

	public Object postProcessAfterInitialization(final Object bean, String beanName)
			throws BeansException {
		System.out.println("第八步:初始化后执行...");
		/**
		 *  我们可以在 这一步，来做增强类的操作
		 */
		// 动态代理:   我们只要对 CustomerService 进行增强
		if(beanName.equals("customerService")){
			Object proxy = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces() , new InvocationHandler() {
				
				// 调用目标方法的时候,就相当于调用invoke方法.
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					
					// 如果我们只对  add 进行增强
					if("add".equals(method.getName())){
						System.out.println("权限校验...");
						Object result = method.invoke(bean, args);
						//System.out.println(System.currentTimeMillis());
						return result;
					}
					
					
					return method.invoke(bean, args);
				}
				
			});
			return proxy;
		}
		return bean;
	}

}
