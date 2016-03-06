package cn.itcast.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;
/**
 * 上传不能够使用BaseServlet
 * @author 杨谦
 * @date 2015-9-4 下午10:01:18
 *
 */
public class Upload3Servlet extends HttpServlet {//文件目录存储
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("text/html;charset=utf-8");
		
		/*
		 * 上传三部曲
		 */
		//工厂
		//F:\file_upload_temp作为零时缓存目录防止撑爆内存条
		DiskFileItemFactory factory = new DiskFileItemFactory(1024*200,new File("F:\\file_upload_temp"));
		//解析器
		ServletFileUpload sfu = new ServletFileUpload(factory);
		//限制大小
//		sfu.setFileSizeMax(100 * 1024);//限制单个文件大小为100k
//		sfu.setSizeMax(1024 * 1024);//限制整个表单的大小为1M
		//如果上传文件超出限制，在praseRequest()方法执行时，会抛出异常
		//解析得到List
		try {
			List<FileItem> list = sfu.parseRequest(request);
			FileItem fi = list.get(1);
			////////////////////////////////////////////////
			/*
			 * 1、得到文件保存的路径
			 */
			///WEB-INF/files/
			String root = this.getServletContext().getRealPath("/upload/");
/*			System.out.println("root: ====="+root);
			String _root = this.getServletContext().getRealPath("/upload/");
			System.out.println("_root:====="+_root);*/
			/*
			 * 2、生成二层目录
			 * 	1）得到文件名称
			 * 	2）得到hashCode
			 * 	3）转发成为16进制
			 * 	4）获取前二个字符用来生成的目录
			 */
			String filename = fi.getName();
			/*
			 * 处理文件名的绝对路径的问题
			 */
			int index = filename.lastIndexOf("\\");
			if(index != -1){
				filename = filename.substring(index+1);
			}
			/*
			 * 给文件名称添加uuid前缀，处理文件名称的同名问题
			 */
			String savename = CommonUtils.uuid()+"_"+filename;
			/*
			 * 1、得到hashCode
			 */
			int hCode = filename.hashCode();
			String hex = Integer.toHexString(hCode);
			/*
			 * 2、获取hex的前两个字母，与root连接在一起，生成一个完整路径
			 */
			File dirFile = new File(root,"/"+hex.charAt(0)+"/"+hex.charAt(1));
			String file_root = "upload/"+hex.charAt(0)+"/"+hex.charAt(1);
			System.out.println(file_root);
			System.out.println(file_root + "/" + savename);
//			File _dirFile = new File(_root,"/"+hex.charAt(0)+"/"+hex.charAt(1));
			/*
			 * 3、创建目录链
			 */
			dirFile.mkdirs();
//			_dirFile.mkdirs();
			/*
			 * 4、创建目标文件
			 */
			File destFile = new File(dirFile,savename);
//			File _destFile = new File(_dirFile,savename);
			/*
			 * 5、保存
			 */
			
			//(upload/upload/2/d/F72E3628CFEC41C2B35E807FFABE9B7C_名企数据结构面试题开篇-v4.pdf)
			fi.write(destFile);
//			fi.write(_destFile);
			////////////////////////////////////////////////
			request.setAttribute("msg", "上传成功");
			request.getRequestDispatcher("/form3.jsp").forward(request, response);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			if(e instanceof FileUploadBase.FileSizeLimitExceededException){
				request.setAttribute("msg", "您上传的文件超过了100KB");
				request.getRequestDispatcher("/form3.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
