package com.noone.Dao;

import java.util.List;

import com.noone.Dao.common.BaseDao;
import com.noone.entity.Sender;
import com.noone.entity.Restaurant;

public interface RestaurantDao extends BaseDao<Restaurant> {

	Restaurant login(String account, String password);

	List<Restaurant> findGoodShop();

	List<Restaurant> findBySearch(String string);

}
