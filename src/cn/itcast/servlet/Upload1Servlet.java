package cn.itcast.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class Upload1Servlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		ServletInputStream in = request.getInputStream();
		/*String s = IOUtils.toString(in);
		System.out.println(s);*/
		//利用commons-fileupload.jar和commons-io.jar并且依赖io包
		
	}

}
