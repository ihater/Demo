import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import cn.itcast.domain.City;
import cn.itcast.domain.Province;

public class Ajax2Servlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		// 1.得到数据
		List<Province> ps = new ArrayList<Province>();

		Province p1 = new Province();
		p1.setName("黑龙江");
		List<City> citys1 = new ArrayList<City>();
		City city1 = new City();
		city1.setName("哈尔滨");
		City city11 = new City();
		city11.setName("齐齐哈尔");
		City city111 = new City();
		city111.setName("大庆");
		citys1.add(city1);
		citys1.add(city11);
		citys1.add(city111);

		p1.setCitys(citys1);

		Province p2 = new Province();
		p2.setName("吉林");
		List<City> citys2 = new ArrayList<City>();
		City city2 = new City();
		city2.setName("长春");
		City city22 = new City();
		city22.setName("吉林");
		City city222 = new City();
		city222.setName("四平");
		citys2.add(city2);
		citys2.add(city22);
		citys2.add(city222);

		p2.setCitys(citys2);

		ps.add(p1);
		ps.add(p2);

		// 转换成json
		String json = JSONArray.fromObject(ps).toString();

		response.getWriter().write(json);
		response.getWriter().close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
