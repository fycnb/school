package com.noone.DaoImpl;

import java.util.List;

import com.noone.Dao.SenderDao;
import com.noone.Dao.RestaurantDao;
import com.noone.DaoImpl.common.BaseDaoImpl;
import com.noone.entity.AllOrder;
import com.noone.entity.Sender;
import com.noone.entity.Restaurant;

public class RestaurantDaolmpl extends BaseDaoImpl<Restaurant> implements RestaurantDao {

	@Override
	public Restaurant login(String account, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from restaurant where phone = " + account
				+ " and password = " + password);
		Restaurant list = this.findOne(sb.toString());
		return list;
	}

	@Override
	public List<Restaurant> findGoodShop() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from restaurant where sale > 100");
		List<Restaurant> list = this.find(sb.toString());
		return list;
	}

	@Override
	public List<Restaurant> findBySearch(String string) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from restaurant where name like '%" + string + "%'");
		List<Restaurant> list = this.find(sb.toString());
		return list;
	}

}
