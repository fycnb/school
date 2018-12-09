package com.noone.Dao;

import java.util.List;

import com.noone.Dao.common.BaseDao;
import com.noone.entity.Detail;

public interface DetailDao extends BaseDao<Detail> {

	List<Detail> findByOrderId(Long id);

}
