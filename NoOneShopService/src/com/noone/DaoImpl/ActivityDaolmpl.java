package com.noone.DaoImpl;

import java.util.List;

import com.noone.Dao.ActivityDao;
import com.noone.Dao.SenderDao;
import com.noone.DaoImpl.common.BaseDaoImpl;
import com.noone.entity.Activity;
import com.noone.entity.AllOrder;
import com.noone.entity.Sender;

public class ActivityDaolmpl extends BaseDaoImpl<Activity> implements ActivityDao {

	@Override
	public List<Activity> findByShopId(int shopid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from activity where shopid = " + shopid);
		List<Activity> list = this.find(sb.toString());
		return list;
	}

}
