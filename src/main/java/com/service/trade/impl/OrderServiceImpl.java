package com.service.trade.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.trade.OrderDao;
import com.model.trade.DOrder;
import com.service.trade.OrderService;
import com.tpadsz.exception.NotExecutedDbException;
import com.utils.DateUtil;
import com.utils.StringUtil;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	private OrderDao orderDao;
	private static Random random = new Random();

	public List<DOrder> getByUser(String uid, int pageNo, int pageSize) throws NotExecutedDbException {
		try {
			List<DOrder> orders = orderDao.getByUser(uid, (pageNo - 1) * pageSize, pageSize);
			return orders;
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:orderDao, method:getByUser", e);
		}
	}

	@Autowired
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void save(DOrder order) throws NotExecutedDbException {
		try {
			order.setId(StringUtil.getUUID());
			order.setSerialno(generateTimeBasedSerialno());
			orderDao.save(order);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:orderDao, method:save", e);
		}
	}

	private String generateTimeBasedSerialno() {
		String serialno = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
		char ch1 = (char) ('A' + random.nextInt(26));
		char ch2 = (char)('a' + random.nextInt(26));
		char ch3 = (char)('a' + random.nextInt(26));
		return serialno + ch1 + ch2 + ch3;
	}
	
	public static void main(String[] args) {
		String serialno = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
		char ch1 = (char) ('A' + random.nextInt(26));
		char ch2 = (char)('a' + random.nextInt(26));
		char ch3 = (char)('a' + random.nextInt(26));
		System.out.println(serialno + ch1 + ch2 + ch3);
	}


}
