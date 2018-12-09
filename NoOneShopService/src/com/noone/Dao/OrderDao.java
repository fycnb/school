package com.noone.Dao;

import java.util.List;

import com.noone.Dao.common.BaseDao;
import com.noone.entity.AllOrder;

public interface OrderDao extends BaseDao<AllOrder> {

	List<AllOrder> findByShopId(int id);

	List<AllOrder> findAllByThree(int shopid, int month, int orderstate,
			int sequence);

	String updateByOrderId(int id, int state);

	AllOrder findOneByOrderId(int orderid);

}
