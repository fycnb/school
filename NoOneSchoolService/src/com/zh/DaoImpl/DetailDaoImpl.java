package com.zh.DaoImpl;

import java.util.List;

import com.zh.Dao.DetailDao;
import com.zh.DaoImpl.common.BaseDaoImpl;
import com.zh.entity.Detail;

public class DetailDaoImpl extends BaseDaoImpl<Detail> implements DetailDao {

	@Override
	public List<Detail> findById(Long orderid) {
		String sql = "select * from detail where orderid = ?";
		List<Detail> list = this.find(sql, orderid);
		return list;
	}
}
