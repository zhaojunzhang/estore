package cn.itcast.web.servlet;
/**
 * ������
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;
import cn.itcast.domin.Orderitem;

public class ExportRankServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��Ҫ������----��ServletContext �л�ð�����
		List<Orderitem> rank = (List<Orderitem>) getServletContext().getAttribute("rank");
		//�ļ����ع���
		//�����ļ����� Content-type
		response.setContentType(getServletContext().getMimeType("rank.csv"));
		//�Ը�����ʽ����Content-Disposition----����������
		Date date =new Date(System.currentTimeMillis());
		String fileName = "estore�̳��������а񡪡�"+date+".csv";
		
		//���ø���������
		String agent = request.getHeader("user-agent");
		if (agent.contains("MSIE")) {
			// IE --- URL����
			fileName = URLEncoder.encode(fileName, "utf-8");
		} else if (agent.contains("Mozilla")) {
			// ���
			BASE64Encoder base64Encoder = new BASE64Encoder();
			fileName = "=?UTF-8?B?"
					+ new String(base64Encoder.encode(fileName
							.getBytes("UTF-8"))) + "?=";
		}
		response.setHeader("Content-Dsiposition", "attachment;filename="+fileName);
		//������Ӧ������  GBK
		response.setCharacterEncoding("gbk");
		//�ļ�����
		PrintWriter out = response.getWriter();
		//��д��ͷ
		out.println("����,��Ʒ���,����,����,��������");
		//����������
		for(int i=0;i<rank.size();i++){
			Orderitem orderitem = rank.get(i);
			//���csv����ʱ������Ʒ���ƣ��������⴦��----ת�壬��"
			out.println((i+1)+","+orderitem.getProduct().getId()+","+convert(orderitem.getProduct().getName())+orderitem.getProduct().getPrice()+","+
					orderitem.getBuynum());
		}
		out.flush();

	}

	private String convert(String name) {
		//���name �д���  "ת��""
		name=name.replace("\"", "\"\"");
		//�������������name����  ���""
		return "\""+name+"\"";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
