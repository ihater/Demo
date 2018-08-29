package cn.itcast.erp.util.interceptor;

import cn.itcast.erp.util.exception.AppException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();				执行我们原来的方法出错的话
		} catch (AppException e) {					e  就是我们刚刚输入的字符串
			//记录日志
			//发送日志到程序员邮箱
			//报警
			ActionSupport as = (ActionSupport) invocation.getAction();	这是为了向 ACtion 里 塞  异常信息
			as.addActionError(e.getMessage());		添加我们的异常 信息	
			return "error";					  <s:property value="actionErrors[0]" /><br />  
		} catch (Exception e) {
//			ActionSupport as = (ActionSupport) invocation.getAction();
//			as.addActionError("对不起，服务器已关闭，请联系管理员！");			上线就返回报错页面
//			return "error";
			//记录日志
			//发送日志到程序员邮箱
			//报警
			e.printStackTrace();							调试就打开console 报错
			return invocation.invoke();						继续执行我们原来的
		}									让页面报错
	}
	
}
