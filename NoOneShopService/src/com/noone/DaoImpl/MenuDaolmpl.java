package com.noone.DaoImpl;

import java.util.List;

import com.noone.Dao.MenuDao;
import com.noone.Dao.SenderDao;
import com.noone.DaoImpl.common.BaseDaoImpl;
import com.noone.entity.AllOrder;
import com.noone.entity.Menu;
import com.noone.entity.Sender;

public class MenuDaolmpl extends BaseDaoImpl<Menu> implements MenuDao {

	@Override
	public List<Menu> findByShopId(int shopid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from menu where restaurantid = " + shopid);
		List<Menu> list = this.find(sb.toString());
		return list;
	}

	@Override
	public List<Menu> findByShopSale(Long shopid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from menu where restaurantid = " + shopid + " and sale>99");
		List<Menu> list = this.find(sb.toString());
		return list;
	}

	@Override
	public List<Menu> findBytype(int type) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from menu where classify = " + type);
		List<Menu> list = this.find(sb.toString());
		return list;
	}

	@Override
	public List<Menu> findShopFoodsBytype(int id, int type) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from menu where restaurantid = " + id);
		if(type==0){
			sb.append(" and sale > 200");	
		}else{
			sb.append(" and classify = " + type);
		}
		List<Menu> list = this.find(sb.toString());
		return list;
	}

	@Override
	public List<Menu> findBySearch(String string) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from menu where name like '%" + string + "%'");
		List<Menu> list = this.find(sb.toString());
		return list;
	}

}
