package cn.itcast.web.servlet;
/**
 * 导出榜单
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
		//需要榜单数据----从ServletContext 中获得榜单数据
		List<Orderitem> rank = (List<Orderitem>) getServletContext().getAttribute("rank");
		//文件下载功能
		//设置文件类型 Content-type
		response.setContentType(getServletContext().getMimeType("rank.csv"));
		//以附件形式下载Content-Disposition----附件名中文
		Date date =new Date(System.currentTimeMillis());
		String fileName = "estore商城销售排行榜——"+date+".csv";
		
		//设置附件中文名
		String agent = request.getHeader("user-agent");
		if (agent.contains("MSIE")) {
			// IE --- URL编码
			fileName = URLEncoder.encode(fileName, "utf-8");
		} else if (agent.contains("Mozilla")) {
			// 火狐
			BASE64Encoder base64Encoder = new BASE64Encoder();
			fileName = "=?UTF-8?B?"
					+ new String(base64Encoder.encode(fileName
							.getBytes("UTF-8"))) + "?=";
		}
		response.setHeader("Content-Dsiposition", "attachment;filename="+fileName);
		//设置响应流编码  GBK
		response.setCharacterEncoding("gbk");
		//文件下载
		PrintWriter out = response.getWriter();
		//先写表头
		out.println("排名,商品编号,名称,单价,销售数量");
		//遍历榜单数据
		for(int i=0;i<rank.size();i++){
			Orderitem orderitem = rank.get(i);
			//输出csv数据时，对商品名称，惊醒特殊处理----转义，和"
			out.println((i+1)+","+orderitem.getProduct().getId()+","+convert(orderitem.getProduct().getName())+orderitem.getProduct().getPrice()+","+
					orderitem.getBuynum());
		}
		out.flush();

	}

	private String convert(String name) {
		//如果name 中存在  "转成""
		name=name.replace("\"", "\"\"");
		//如果包含，返回name两端  添加""
		return "\""+name+"\"";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
