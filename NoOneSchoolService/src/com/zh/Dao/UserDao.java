package com.zh.Dao;

import com.zh.Dao.common.BaseDao;
import com.zh.entity.User;

public interface UserDao extends BaseDao<User> {

	int changeNickname(String nickname, String userid);

	int changePwd(String newpw, String userid);

}
