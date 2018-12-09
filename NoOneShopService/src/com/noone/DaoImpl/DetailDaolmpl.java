package com.noone.DaoImpl;

import java.util.List;

import com.noone.Dao.ActivityDao;
import com.noone.Dao.DetailDao;
import com.noone.Dao.SenderDao;
import com.noone.DaoImpl.common.BaseDaoImpl;
import com.noone.entity.Activity;
import com.noone.entity.AllOrder;
import com.noone.entity.Detail;
import com.noone.entity.Menu;
import com.noone.entity.Sender;

public class DetailDaolmpl extends BaseDaoImpl<Detail> implements DetailDao {

	@Override
	public List<Detail> findByOrderId(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from detail where orderid = " + id);
		List<Detail> list = this.find(sb.toString());
		return list;
	}

}
