package cn.itcast.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.itcast.dao.RankDAO;
import cn.itcast.domin.Orderitem;

public class RankService {
	//�鿴��

	public List<Orderitem> showRank() {
		RankDAO rankDAO = new RankDAO();
		List<Orderitem> rank = rankDAO.findRankData();
		//�鿴����  ��һ�������  ------��  Ӧ�ð���������������
		Collections.sort(rank,new RankComparator());
		// ����ʵ��Compareable
		// �ӿڣ�������Ҫ�ṩ�������Ƚ���
		return rank;
	}
	class RankComparator implements Comparator<Orderitem>{

		@Override
		public int compare(Orderitem o1, Orderitem o2) {
			//���� ǰ-��   ���� ��-ǰ
			return o2.getBuynum()-o1.getBuynum();
		}
		
	}

}
