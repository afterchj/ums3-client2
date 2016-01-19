package com.service.trade;

import java.util.List;

import com.model.trade.DOrder;
import com.tpadsz.exception.NotExecutedDbException;

public interface OrderService {

	List<DOrder> getByUser(String uid, int pageNo, int pageSize) throws NotExecutedDbException;

	void save(DOrder order) throws NotExecutedDbException;

}
