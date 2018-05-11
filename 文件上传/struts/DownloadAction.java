package cn.itcast.action;
/**
 *  2.下载
		文件下载方式:
			1.超连接(超链接是 get 请求的)
			2.服务器编码，通过流向客户端写回。
				
				1.通过response设置  response.setContentType(String mimetype);
				2.通过response设置  response.setHeader("Content-disposition;filename=xxx");
				3.通过response获取流，将要下载的信息写出。
 *
 *
 *
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import cn.itcast.utils.DownloadUtils;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadAction extends ActionSupport {

	private String filename; // 要下载文件的名称

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	// 设置下载文件mimeType类型
	public String getContentType() {
		// 根据文件名获取   mimeType
		String mimeType = ServletActionContext.getServletContext().getMimeType(
				filename);   
		return mimeType;
	}

	// 获取下载文件名称
	public String getDownloadFileName() throws UnsupportedEncodingException {

		/**
		 *   解决浏览器不同的乱码问题，还有  我们前台 	<a href="${pageContext.request.contextPath}/download?filename=a.txt">a.txt</a>
		 *   然后我们在   <param name="contentDisposition">attachment;filename=a.txt</param> 是写死的！！！！我们可不可以不写死 
		 *   
		 *   <param name="contentDisposition">attachment;filename=${downloadFileName}</param>  注意ongl 表达式和方法名的关系
		 */
		return DownloadUtils.getDownloadFileName(ServletActionContext
				.getRequest().getHeader("user-agent"), filename);

	}

	public InputStream getInputStream() throws FileNotFoundException,
			UnsupportedEncodingException {

		// 我们前台用的是你超链接进行请求下载的   所以我们用的是  get 方式进行请求，所以会有乱码问题
		filename = new String(filename.getBytes("iso8859-1"), "utf-8"); // 解决中文名称乱码.

		FileInputStream fis = new FileInputStream("d:/upload/" + filename);
		return fis;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("进行下载....");
		return SUCCESS;
	}

}
