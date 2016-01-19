package com.dao.trade;

import java.util.List;

import com.model.trade.DShelf;

public interface ShelfDao {

	List<DShelf> getAll(int status);

	DShelf getById(String sid);

}
