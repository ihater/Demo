package cn.itcast.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

/**
 * 用于操作文件的工具类
 * @author zhaoqx
 *
 */
public class UploadFileUtils {
	//将临时文件保存到指定的目录中
	public static String copy(File resource) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
		String dateStr = sdf.format(date);
		System.err.println(dateStr);
		
		//日期目录结构 
		File dateDir = new File("d:\\uploadFiles" + dateStr);
		if(!dateDir.exists()){
			dateDir.mkdirs();
		}
		
		//构造目标文件
		File target = new File(dateDir.getPath()+File.separator+UUID.randomUUID().toString());
		
		try {
			FileUtils.copyFile(resource, target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target.getPath();
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd");
		String dateStr = sdf.format(date);
		System.err.println(dateStr);
		
		File dateDir = new File("d:\\uploadFiles" + dateStr);
		if(!dateDir.exists()){
			dateDir.mkdirs();
		}
		System.out.println(dateDir.getPath());
	}

}
