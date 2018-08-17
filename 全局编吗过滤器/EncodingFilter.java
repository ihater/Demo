package cn.itcast.filter.demo6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EncodingFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		// 1.强制转换
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// 2.操作
		HttpServletRequest myrequest = new MyRequest(request); // 增强后的request,解决了编码问题
		response.setContentType("text/html;charset=utf-8");

		// 3.放行
		chain.doFilter(myrequest, response);

	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}

// 装饰类，继承同一接口或同一接口接口的实现类
class MyRequest extends HttpServletRequestWrapper {

	private HttpServletRequest request;

	public MyRequest(HttpServletRequest request) {
		super(request);// 因为继承HttpServletRequestWrapper，没有无参构造，要手动指定父类
		this.request = request;
	}

	// 重写关于获取请求参数的方法.
	@Override
	public String getParameter(String name) {
		Map<String, String[]> map = getParameterMap();

		if (name == null) {
			return null;
		}
		String[] st = map.get(name);
		if (st == null || st.length == 0) {
			return null;
		}

		return st[0];
	}

	@Override
	public String[] getParameterValues(String name) {
		Map<String, String[]> map = getParameterMap();

		if (name == null) {
			return null;
		}
		String[] st = map.get(name);

		return st;

	}

	private boolean flag = true;

	@Override
	public Map getParameterMap() {
		// 1.得到所有请求参数的Map集合
		Map<String, String[]> map = request.getParameterMap(); // 有编码问题.

		// 2.解决编码问题.
		if (flag) {
			for (String key : map.keySet()) {
				String[] values = map.get(key);

				for (int i = 0; i < values.length; i++) {
					try {
						values[i] = new String(values[i].getBytes("iso8859-1"),
								"utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

			}
			flag = false;
		}
		return map;
	}

}
