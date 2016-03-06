package cn.itcast.download;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class DownloadServlet1 extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("text/html;charset=UTF-8");
		/*
		 * 两个头一个流
		 * 1、Content-Type
		 * 2、Content-Disposition
		 * 3、流：文件的数据
		 */
		String filename = "F:/孙子涵-唐人.mp3";
		String[] strArr = filename.split("/");
//		String framename = new String(strArr[strArr.length-1].getBytes("GBK"),"ISO-8859-1");
		String framename = DownUtils.filenameEncoding(strArr[strArr.length-1], request);
		String contentType = this.getServletContext().getMimeType(filename);
		String contentDisposition = "attachment;filename="+framename;
		//一个流
		FileInputStream fin = new FileInputStream(filename);
		//设置两个头
		response.setHeader("Content-Type", contentType);
		response.setHeader("Content-Disposition", contentDisposition);
		ServletOutputStream output = response.getOutputStream();
		
		IOUtils.copy(fin, output);//把输入流数据写给output
		
		fin.close();
	}

}
