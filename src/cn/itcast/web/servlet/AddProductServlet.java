package cn.itcast.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.domin.Product;
import cn.itcast.service.ProductService;
import cn.itcast.utils.PicUtils;
import cn.itcast.utils.UploadUtils;

public class AddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String,String> parameterMap = new HashMap<String,String>();
		if(ServletFileUpload.isMultipartContent(request)){
			//1,工厂
			DiskFileItemFactory  diskfileItemfactory = new DiskFileItemFactory();
			//2,工厂或得解析器
			ServletFileUpload fileUpload = new ServletFileUpload(diskfileItemfactory);
			//3,设置解析器参数
			fileUpload.setFileSizeMax(1024*1024*5);//5MB  文件不能超过5MB
			fileUpload.setHeaderEncoding("utf-8");
			//4,解析器解析请求request
			
			try {
				List<FileItem> list =fileUpload.parseRequest(request);
				//5,遍历list集合
				for(FileItem fileItem:list){
				//6,判断FileItem是文件上传项还是普通Form项
					if(fileItem.isFormField()){
						//普通form项
						//获得name和value
						String name = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						parameterMap.put(name, value);
						
					}else{
						//文件上传项、
						//判断用户是否上传文件
						String fileName = fileItem.getName();
						if(fileName==null||fileName.trim().equals("")){
							//没有上传图片
							throw new RuntimeException("添加商品，必须要上传图片");
						}
						//获得真实文件名---早起浏览器上传文件时  带有路径
						fileName = UploadUtils.subFileName(fileName);
						//校验上传文件  格式---根据文件扩展名
						String contentType = fileItem.getContentType();
						boolean isVaild = UploadUtils.checkImgType(contentType);
						if(!isVaild){
							//格式无效
							throw new RuntimeException("上传图片格式不正确的！");
							
						}
						//唯一UUID  随机文件名
						String uuidname = UploadUtils.generateUUIDName(fileName);
						
						//分散目录生成
						String randomDir = UploadUtils.generateRandomDir(uuidname);
						//创建随机目录
						File dir = new File(getServletContext().getRealPath("/upload"+randomDir));
						dir.mkdirs();
						//文件上传
						InputStream in = new BufferedInputStream(fileItem.getInputStream());
						OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(dir,uuidname)));
						int b;
						while((b=in.read())!=-1){
							out.write(b);
							
						}
						out.close();
						in.close();
						
						//删除  临时文件
						fileItem.delete();
						
						//添加自动生成小图代码
						PicUtils picUtils = new PicUtils(getServletContext().getRealPath("/upload"+randomDir+"/"+uuidname));
						picUtils.resize(100,100);
						
						//将img保存路径  存放parameterMap
						parameterMap.put("img","/upload"+randomDir+"/"+uuidname);
						
					}
				}
				//保存  商品信息   到数据库  ----封装 JavaBean
				Product product = new Product();
				try {
					BeanUtils.populate(product, parameterMap);
					
					//传递 javabean 给业务层
					ProductService productservice = new ProductService();
					productservice.addproduct(product);
					
					//决定跳转页面
					response.sendRedirect("/index.jsp");
					
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else{
			throw new RuntimeException("添加商品，必须使用文件上传表单");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
