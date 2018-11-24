package com.zh.DaoImpl;

import com.zh.Dao.UserDao;
import com.zh.DaoImpl.common.BaseDaoImpl;
import com.zh.entity.User;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public int changeNickname(String nickname, String userid) {
		String sql = "update user set nickname = ?  where id = ?";
		int rs = this.update(sql, nickname, userid);
		return rs;
	}

	@Override
	public int changePwd(String newpw, String userid) {
		String sql = "update user set password = ?  where id = ?";
		int rs = this.update(sql, newpw, userid);
		return rs;
	}
}
