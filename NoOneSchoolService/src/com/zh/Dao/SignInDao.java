package com.zh.Dao;

import java.util.List;

import com.zh.Dao.common.BaseDao;
import com.zh.entity.SignIn;

public interface SignInDao extends BaseDao<SignIn> {

	List<SignIn> findById(String userid, String year, String month);

}