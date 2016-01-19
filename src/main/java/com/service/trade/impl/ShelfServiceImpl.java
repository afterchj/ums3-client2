package com.service.trade.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.trade.ShelfDao;
import com.model.trade.DShelf;
import com.service.trade.ShelfService;
import com.tpadsz.exception.NotExecutedDbException;

@Service("shelfService")
public class ShelfServiceImpl implements ShelfService {
	
	private ShelfDao shelfDao;
	private Logger logger = Logger.getLogger(ShelfServiceImpl.class);

	public List<DShelf> getAll(int enable) throws NotExecutedDbException {
		List<DShelf> shelvies = null;
		try {
			shelvies = shelfDao.getAll(enable);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:shelfDao, method:getAll", e);
		}
		return shelvies;
	}

	public DShelf getById(String sid) throws NotExecutedDbException {
		DShelf shelf = null;
		try {
			shelf = shelfDao.getById(sid);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:shelfDao, method:getById", e);
		}
		return shelf;
	}

	@Autowired
	public void setShelfDao(ShelfDao shelfDao) {
		this.shelfDao = shelfDao;
	}

}
