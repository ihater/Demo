package cn.itcast.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig								// 表示 Servlet 接受 multipart /  form-data  请求
public class UploadServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");// 解决乱码

//		<form action="${pageContext.request.contextPath}/upload" method="post"
//				enctype="multipart/form-data">
//				<input type="file" name="f"><input type="submit" value="上传">
//			</form>
		
		Part part = req.getPart("f"); // 得到上传文件信息.
		
//		req.getParts();

		// 获取上传文件名称
		String cd = part.getHeader("Content-Disposition");

		System.out.println(cd); // form-data; name="f";
								// filename="C:\Users\Administrator\Desktop\鎹曡幏.PNG"

		/**
		 *    接下来 通过 获取的  Content-Disposition   截取  获取 文件名
		 */
		String filename = cd.substring(cd.lastIndexOf("\\") + 1,
				cd.length() - 1);

		System.out.println(filename);

		part.write("d:/upload/"+filename);// 完成文件上传.

	}

}
