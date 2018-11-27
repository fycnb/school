package com.zh.Dao;

import java.util.List;

import com.zh.Dao.common.BaseDao;
import com.zh.entity.Detail;

public interface DetailDao extends BaseDao<Detail> {

	List<Detail> findById(Long orderid);

}
