package cn.itcast.erp.util.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;

***************************************************************************************************************************************************
这个demo：是在 拦截所有请求，做权限校验，看看需不需要做校验，因为有些请求对所有的角色都不需要校验，比如登录
但是如果有500个请求路径，每次都要在 拦截器 里 比较，看看需不需要做校验
每次都要查询所有请求，再比较，就有瓶颈，所以我们把所有需要拦截校验的请求 放到 buffer，
***************************************************************************************************************************************************
—————————————————————————————————————————————————————
—————————————————————————————————————————————————————
我们在用 listen 的时候，spring 可能还没启动呢，所以注入拿到 对象，是不可能的
（但是，拦截器可以拿到注入对象，因为拦截器对象由struts创建，因此具有自动装配功能（struts-spring-plugin.jar））
这就要注意配置文件的顺序了，， web.xml 配置中，spring 的监听器要 配置在  这个监听器之后

  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <listener>
    <listener-class>cn.itcast.erp.util.listener.AllResLoadListener</listener-class>
  </listener>
—————————————————————————————————————————————————————
—————————————————————————————————————————————————————



public class AllResLoadListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent event) {

		//读取所有资源信息，放入SerlvetContext范围
		//使用spring的上下文对象

		ServletContext sc = event.getServletContext();

		
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc); 		这里 只能通过  Spring 上下文来获取注入对象
		ResEbi resEbi = (ResEbi) ctx.getBean("resEbi");
		List<ResModel> resList = resEbi.getAll();


		StringBuilder sbf = new StringBuilder();				把全局资源放入  buffer
		for(ResModel temp :resList){
			sbf.append(temp.getUrl());
			sbf.append(",");
		}

		//放入sc中
		sc.setAttribute("allRes", sbf.toString());
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
	}
}




