package com.zh.DaoImpl;

import java.util.List;

import com.zh.Dao.SignInDao;
import com.zh.DaoImpl.common.BaseDaoImpl;
import com.zh.entity.SignIn;

public class SignInDaoImpl extends BaseDaoImpl<SignIn> implements SignInDao {

	@Override
	public List<SignIn> findById(String userid, String year, String month) {
		String sql = "select day from signin where userid = ? and year = ? and month = ?";
		List<SignIn> list = this.find(sql, userid, year, month);
		return list;
	}
}
