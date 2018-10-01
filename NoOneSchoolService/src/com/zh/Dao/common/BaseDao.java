package com.zh.Dao.common;

import java.util.List;

import com.zh.entity.common.Entity;
import com.zh.entity.common.Page;

/**
 * 基于泛型的�?用DAO接口
 * 
 * @param <T>
 */
public interface BaseDao<T extends Entity> {

	/**
	 * 保存�?��实例
	 * 
	 * @param entity
	 *            要保存的实例
	 */
	public T save(T entity) throws DaoException;

	/**
	 * 根据主键删除�?��实例
	 * 
	 * @param id
	 *            主键�?
	 */
	public void delete(Long id) throws DaoException;

	/**
	 * 编辑指定实例的详细信�?
	 * 
	 * @param entity
	 *            实例
	 */
	public int update(T entity) throws DaoException;

	/**
	 * 判断指定主键对应的实体是否存�?
	 * 
	 * @param id
	 *            主键�?
	 * @return 存在返回true;否则返回false
	 */
	public boolean exists(Long id) throws DaoException;

	/**
	 * 根据主键获取对应的实�?
	 * 
	 * @param id
	 *            主键�?
	 * @return 如果查询成功，返回符合条件的实例;如果查询失败，返回null
	 */
	public T findOne(Long id) throws DaoException;

	/**
	 * 获取�?��实体实例的列�?
	 * 
	 * @return 符合条件的实例列�?
	 */
	public List<T> findAll() throws DaoException;
	/**
	 * 统计总实体实例的数量
	 * 
	 * @return 总数�?
	 */
	public long count() throws DaoException;

	/**
	 * 获取分页列表
	 * 
	 * @param page
	 *            当前页号
	 * @param size
	 *            每页要显示的记录�?
	 * @return 符合分页条件的分页模型实�?
	 */
	public Page<T> findAll(int page, int size) throws DaoException;

	/**
	 * 根据指定的SQL语句和参数�?执行更新数据的操�?
	 * 
	 * @param sql
	 *            SQL语句
	 * @param paramValues
	 *            参数值数�?
	 * @return 
	 */
	public int update(String sql, Object... paramValues) throws DaoException;

	/**
	 * 根据指定的SQL语句和参数�?执行查询数据的操�?
	 * 
	 * @param sql
	 *            SQL语句
	 * @param paramValues
	 *            参数�?
	 * @return 符合条件的实体对象列�?
	 */
	public List<T> find(String sql, Object... paramValues) throws DaoException;

	/**
	 * 根据指定的SQL语句和参数�?执行单个对象的查询操�?
	 * 
	 * @param sql
	 *            SQL语句
	 * @param paramValues
	 *            参数�?
	 * @return 符合条件的实体对�?
	 */
	public T findOne(String sql, Object... paramValues) throws DaoException;
}
