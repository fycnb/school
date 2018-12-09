package com.noone.Dao;

import java.util.List;

import com.noone.Dao.common.BaseDao;
import com.noone.entity.Activity;
import com.noone.entity.Sender;

public interface ActivityDao extends BaseDao<Activity> {

	List<Activity> findByShopId(int shopid);

}
