package com.zh.Dao;

import com.zh.Dao.common.BaseDao;
import com.zh.entity.Taker;

public interface TakerDao extends BaseDao<Taker> {

	int changePwd(String newpw, String takerid);

	Taker Login(String account, String password);


}
