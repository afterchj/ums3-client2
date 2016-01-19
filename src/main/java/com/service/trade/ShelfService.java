package com.service.trade;

import java.util.List;

import com.model.trade.DShelf;
import com.tpadsz.exception.NotExecutedDbException;

public interface ShelfService {

	List<DShelf> getAll(int enable) throws NotExecutedDbException;

	DShelf getById(String sid) throws NotExecutedDbException;

}
