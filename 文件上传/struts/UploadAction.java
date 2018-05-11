package cn.itcast.action;
/**
	1.上传要做什么（	原因:超连接是get请求，并且下载的文件是中文名称，乱码。）
		浏览器端:
			1.method=post
			2.<input type="file" name="xx">
			3.encType="multipart/form-data";
			
		服务器端:
			commons-fileupload组件
			1.DiskFileItemFactory
			2.ServletFileUpload
			3.FileItem
			
				
 		struts2中文件上传:
			默认情况下struts2框架使用的就是commons-fileupload组件.
			struts2它使用了一个interceptor帮助我们完成文件上传操作。我们只需要做二次开发就好了
			 <interceptor name="fileUpload" class="org.apache.struts2.interceptor.FileUploadInterceptor"/>
			 
			 
	*** 在action中怎样处理文件上传?
			页面上组件:<input type="file" name="upload">
			
			在action中要有三个属性:
				private File upload;
				private String uploadContentType;
				private String uploadFileName;
				
			在execute方法中使用commons-io包下的FileUtils完成文件复制.			
				FileUtils.copyFile(upload, new File("d:/upload",uploadFileName));
				
			在web.xml 文件中：
				<action name="upload" class="cn.itcast.action.UploadAction">
					<result name="input">/upload.jsp</result>
					<interceptor-ref name="defaultStack">
						<param name="maximumSize">2097152</param>
						<param name="fileUpload.allowedExtensions">txt,mp3,doc</param>
					</interceptor-ref>
					</action>
		------------------------------------------------------------------------
 * 
 */
import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {

	/**
	 *  我们要做多文件上传，只需要将  upload 设置成集合或者数组就可以了
	 *  private File upload;   单文件的时候  
	 */
	// 在action类中需要声明三个属性
	private List<File> upload;           // 这个名，<input type="file" name="upload"><br>  得跟前台的组件的名字一样
	/**
	 *  这里uploadContentType  // 上传文件的类型   这个名字也不是瞎写的    （组件名）+（文件类型）
	 *  在Struts 框架的上传组件的源码中~~~~~~~~会获取到这个 上传文件的类型
	 */
	private List<String> uploadContentType;  
	private List<String> uploadFileName;   // 上传文件的名字，名字不是瞎写的

	public List<File> getUpload() {
		return upload;
	}

	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public List<String> getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(List<String> uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public List<String> getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * struts 默认使用的上传组件是   commons-fileupload  ，我们也可以去改成其他 的上传方式
	 * 在 struts-default.xml  的配置文件中，还有  cos  pell 的上传组件，只是被注释掉了
	 * 我们打开注释，就可以用 cos 或者 pell 的上传组件了，但是我们要额外导入  jar 包
	 */
	@Override
	public String execute() throws Exception {

		//因为我们从前台拿到的多个File ，是被封装到一个 List 集合里面去的 
		for (int i = 0; i < upload.size(); i++) {
			
			
			System.out.println("上传文件的类型:" + uploadContentType.get(i));
			System.out.println("上传文件的名称:" + uploadFileName.get(i));

			// 完成文件上传.用工具类
			/**
			 * FileUtils.copyFile(upload , new File("路径"，"文件名"))
			 */
			FileUtils.copyFile(upload.get(i), new File("d:/upload", uploadFileName.get(i)));
		}
		return null;
	}

}
