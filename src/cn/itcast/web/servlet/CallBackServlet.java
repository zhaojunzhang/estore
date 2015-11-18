package cn.itcast.web.servlet;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.service.OrderService;
import cn.itcast.utils.PaymentUtil;

/**
 * 回调程序
 * 
 * @author seawind
 * 
 */
public class CallBackServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 校验 返回数据 有效性
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");

		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 验证数字签名有效性
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 数字签名有效
			// 收到支付 结果 有两种 ： 客户端浏览器重定向、易宝通知
			if (r9_BType.equals("1")) {
				// 客户端浏览器重定向
				response.getWriter().println(
						"付款成功！订单号：" + r6_Order + ",金额：" + r3_Amt);
				// 这里不要修改订单状态
			} else if (r9_BType.equals("2")) {
				// 易宝通知收到付款
				// 修改订单状态
				OrderService orderService = new OrderService();
				orderService.pay(r6_Order);

				// 通知易宝接收消息
				response.getWriter().print("success");

				System.out.println("订单:" + r6_Order + "已经付款！");
			}
		} else {
			// 签名无效
			throw new RuntimeException("数据已经被篡改！");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
