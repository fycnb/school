package com.noone.Dao;

import java.util.List;

import com.noone.Dao.common.BaseDao;
import com.noone.entity.Menu;
import com.noone.entity.Sender;

public interface MenuDao extends BaseDao<Menu> {

	List<Menu> findByShopId(int shopid);

	List<Menu> findByShopSale(Long shopid);

	List<Menu> findBytype(int type);

	List<Menu> findShopFoodsBytype(int id, int type);

	List<Menu> findBySearch(String string);

}
