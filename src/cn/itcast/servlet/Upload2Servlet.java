package cn.itcast.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;

public class Upload2Servlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		/**
		 * 上传三部曲
		 * 1、得到工程
		 * 2、通过工程创建解析器
		 * 3、解析request
		 * 4、得到FileItm集合，调用器QPI完成晚间的保存
		 */
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setHeaderEncoding("utf-8");
		try {
			List<FileItem> fileItemList = sfu.parseRequest(request);
			FileItem fi1 = fileItemList.get(0);
			FileItem fi2 = fileItemList.get(1);
			
			System.out.println("普通表单项演示："+fi1.getFieldName()+
					"="+fi1.getString("utf-8"));
			System.out.println("文件表单项：");
			System.out.println("Content-Type:"+fi2.getContentType());
			System.out.println("size:"+fi2.getSize());
			String filename = fi2.getName();
			//防止绝对路径
			int lastIndex = filename.lastIndexOf("\\");
			if(lastIndex!=-1){
				filename = filename.substring(lastIndex+1);
			}
			//防止文件名乱码
			filename = CommonUtils.uuid()+ "_" + filename;
			System.out.println(filename);
			System.out.println("filename:"+filename);
			//保存文件
			File destFile = new File("F:/"+filename);
			fi2.write(destFile);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
