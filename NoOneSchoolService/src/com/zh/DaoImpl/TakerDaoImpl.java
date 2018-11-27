package com.zh.DaoImpl;

import com.zh.Dao.TakerDao;
import com.zh.DaoImpl.common.BaseDaoImpl;
import com.zh.entity.Taker;

public class TakerDaoImpl extends BaseDaoImpl<Taker> implements TakerDao {

	@Override
	public int changePwd(String newpw, String takerid) {
		String sql = "update taker set password = ?  where id = ?";
		int rs = this.update(sql, newpw, takerid);
		return rs;
	}

	@Override
	public Taker Login(String account, String password) {
		String sql = "select * from taker where account = ? and password = ?";
		Taker taker = this.findOne(sql, account,password);
		return taker;
	}

}
