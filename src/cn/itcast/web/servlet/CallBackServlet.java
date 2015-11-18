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
 * �ص�����
 * 
 * @author seawind
 * 
 */
public class CallBackServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// У�� �������� ��Ч��
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

		// ��֤����ǩ����Ч��
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// ����ǩ����Ч
			// �յ�֧�� ��� ������ �� �ͻ���������ض����ױ�֪ͨ
			if (r9_BType.equals("1")) {
				// �ͻ���������ض���
				response.getWriter().println(
						"����ɹ��������ţ�" + r6_Order + ",��" + r3_Amt);
				// ���ﲻҪ�޸Ķ���״̬
			} else if (r9_BType.equals("2")) {
				// �ױ�֪ͨ�յ�����
				// �޸Ķ���״̬
				OrderService orderService = new OrderService();
				orderService.pay(r6_Order);

				// ֪ͨ�ױ�������Ϣ
				response.getWriter().print("success");

				System.out.println("����:" + r6_Order + "�Ѿ����");
			}
		} else {
			// ǩ����Ч
			throw new RuntimeException("�����Ѿ����۸ģ�");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
