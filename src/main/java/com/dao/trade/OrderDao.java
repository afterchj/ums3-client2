package com.dao.trade;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.trade.DOrder;

public interface OrderDao {

	List<DOrder> getByUser(@Param("uid")String uid, @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);

	void save(DOrder order);

}
