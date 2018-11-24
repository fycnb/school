package com.zh.DaoImpl;

import java.util.List;

import com.zh.Dao.OrderDao;
import com.zh.DaoImpl.common.BaseDaoImpl;
import com.zh.entity.Indent;

public class OrderDaoImpl extends BaseDaoImpl<Indent> implements OrderDao {

	@Override
	public int cancelOrder(String userid, String orderid) {
		String sql = "update indent set state = -1  where userid = ? and id = ?";
		int rs = this.update(sql, userid, orderid);
		return rs;
	}

	@Override
	public List<Indent> findAllById(String userid) {
		String sql = "select * from indent where userid = ? and state in(-1,0,1,4,5) order by state";
		List<Indent> orderlist = this.find(sql, userid);
		return orderlist;
	}

	@Override
	public List<Indent> findByState(String userid, String sta) {
		String sql = "select * from indent where userid = ? and state = ?";
		List<Indent> orderlist = this.find(sql, userid, sta);
		return orderlist;
	}


	@Override
	public List<Indent> findByTaker0() {
		String sql = "select * from indent where  state = 1 and takerid = 0 ";
		List<Indent> orderlist = this.find(sql);
		return orderlist;
	}

	@Override
	public int updateByTakerid(String orderid, String takerid) {
		String sql = "update indent set takerid = ? where id = ?";
		int rs = this.update(sql, takerid,orderid);
		return rs;
	}

	@Override
	public List<Indent> findByTaker(String takerid) {
		String sql = "select * from indent where  state in(1,2,3) and takerid = ? ";
		List<Indent> orderlist = this.find(sql,takerid);
		return orderlist;
	}

	@Override
	public int update3ofstate(String orderid, String takerid) {
		String sql = "update indent set state = 3  where takerid = ? and id = ?";
		int rs = this.update(sql, takerid, orderid);
		return rs;
	}

	@Override
	public int update4ofstate(String orderid, String takerid) {
		String sql = "update indent set state = 4  where takerid = ? and id = ?";
		int rs = this.update(sql, takerid, orderid);
		return rs;
	}

	@Override
	public List<Indent> findHistoryByTaker(String takerid) {
		String sql = "select * from indent where takerid = ? and state in (4,5)";
		List<Indent> orderlist = this.find(sql, takerid);
		return orderlist;
	}

	@Override
	public int update5ofstate(int userid, int orderid) {
		String sql = "update indent set state = 5  where userid = ? and id = ?";
		int rs = this.update(sql, userid, orderid);
		return rs;
	}
}