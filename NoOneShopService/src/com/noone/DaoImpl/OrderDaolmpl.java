package com.noone.DaoImpl;

import java.util.List;

import com.noone.Dao.OrderDao;
import com.noone.DaoImpl.common.BaseDaoImpl;
import com.noone.entity.AllOrder;

public class OrderDaolmpl extends BaseDaoImpl<AllOrder> implements OrderDao {

	@Override
	public List<AllOrder> findByShopId(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from allorder where restaurantid = " + id
				+ " and state < 2 and state > -1 ");
		List<AllOrder> list = this.find(sb.toString());
		return list;
	}

	@Override
	public List<AllOrder> findAllByThree(int shopid, int month, int orderstate,
			int sequence) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from allorder where restaurantid = " + shopid);
		if (orderstate == 1) {
			sb.append(" and state = 3");
		} else if (orderstate == 2) {
			sb.append(" and state < 3");
		}
		if (month == 1) {
			sb.append(" and DATE_FORMAT( time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
		} else if (month == 2) {
			sb.append(" and DATE_SUB(CURDATE(), INTERVAL 3 MONTH) <= date(time)");
		}
		if (sequence == 0) {
			sb.append(" order by id asc");
		} else {
			sb.append(" order by id desc");
		}
		List<AllOrder> list = this.find(sb.toString());
		return list;
	}

	@Override
	public String updateByOrderId(int id, int state) {
		StringBuilder sb = new StringBuilder();
		if (state != -1)
			sb.append(" UPDATE allorder SET state = " + state
					+ " WHERE state = " + (state - 1)
					+ (state == 2 ? " and takerid<>0" : "")
					+ " and orderid = " + id);
		else
			sb.append(" UPDATE allorder SET state = " + state
					+ " WHERE state = 0 and takerid = " + id);
		return this.update(sb.toString());
	}

	@Override
	public AllOrder findOneByOrderId(int orderid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from allorder where id = " + orderid);
		AllOrder list = this.findOne(sb.toString());
		return list;
	}

}
