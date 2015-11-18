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
			//1,����
			DiskFileItemFactory  diskfileItemfactory = new DiskFileItemFactory();
			//2,������ý�����
			ServletFileUpload fileUpload = new ServletFileUpload(diskfileItemfactory);
			//3,���ý���������
			fileUpload.setFileSizeMax(1024*1024*5);//5MB  �ļ����ܳ���5MB
			fileUpload.setHeaderEncoding("utf-8");
			//4,��������������request
			
			try {
				List<FileItem> list =fileUpload.parseRequest(request);
				//5,����list����
				for(FileItem fileItem:list){
				//6,�ж�FileItem���ļ��ϴ������ͨForm��
					if(fileItem.isFormField()){
						//��ͨform��
						//���name��value
						String name = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						parameterMap.put(name, value);
						
					}else{
						//�ļ��ϴ��
						//�ж��û��Ƿ��ϴ��ļ�
						String fileName = fileItem.getName();
						if(fileName==null||fileName.trim().equals("")){
							//û���ϴ�ͼƬ
							throw new RuntimeException("�����Ʒ������Ҫ�ϴ�ͼƬ");
						}
						//�����ʵ�ļ���---����������ϴ��ļ�ʱ  ����·��
						fileName = UploadUtils.subFileName(fileName);
						//У���ϴ��ļ�  ��ʽ---�����ļ���չ��
						String contentType = fileItem.getContentType();
						boolean isVaild = UploadUtils.checkImgType(contentType);
						if(!isVaild){
							//��ʽ��Ч
							throw new RuntimeException("�ϴ�ͼƬ��ʽ����ȷ�ģ�");
							
						}
						//ΨһUUID  ����ļ���
						String uuidname = UploadUtils.generateUUIDName(fileName);
						
						//��ɢĿ¼����
						String randomDir = UploadUtils.generateRandomDir(uuidname);
						//�������Ŀ¼
						File dir = new File(getServletContext().getRealPath("/upload"+randomDir));
						dir.mkdirs();
						//�ļ��ϴ�
						InputStream in = new BufferedInputStream(fileItem.getInputStream());
						OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(dir,uuidname)));
						int b;
						while((b=in.read())!=-1){
							out.write(b);
							
						}
						out.close();
						in.close();
						
						//ɾ��  ��ʱ�ļ�
						fileItem.delete();
						
						//����Զ�����Сͼ����
						PicUtils picUtils = new PicUtils(getServletContext().getRealPath("/upload"+randomDir+"/"+uuidname));
						picUtils.resize(100,100);
						
						//��img����·��  ���parameterMap
						parameterMap.put("img","/upload"+randomDir+"/"+uuidname);
						
					}
				}
				//����  ��Ʒ��Ϣ   �����ݿ�  ----��װ JavaBean
				Product product = new Product();
				try {
					BeanUtils.populate(product, parameterMap);
					
					//���� javabean ��ҵ���
					ProductService productservice = new ProductService();
					productservice.addproduct(product);
					
					//������תҳ��
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
			throw new RuntimeException("�����Ʒ������ʹ���ļ��ϴ���");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
