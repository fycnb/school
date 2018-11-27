package com.zh.Dao;

import java.util.List;

import com.zh.Dao.common.BaseDao;
import com.zh.entity.Indent;

public interface OrderDao extends BaseDao<Indent> {

	int cancelOrder(String userid, String orderid);

	List<Indent> findAllById(String userid);

	List<Indent> findByState(String userid, String sta);

	List<Indent> findByTaker0();

	int updateByTakerid(String orderid, String takerid);

	List<Indent> findByTaker(String takerid);

	int update3ofstate(String orderid, String takerid);

	int update4ofstate(String orderid, String takerid);

	List<Indent> findHistoryByTaker(String takerid);

	int update5ofstate(int userid, int orderid);


}