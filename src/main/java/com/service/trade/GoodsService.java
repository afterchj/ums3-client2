package com.service.trade;

import java.util.List;

import com.exception.GoodsNotEnoughException;
import com.exception.GoodsNotFoundException;
import com.model.trade.DGoods;
import com.model.trade.DOrder;
import com.model.trade.RExpressPost;
import com.tpadsz.cic.coin.exception.CoinsNotEnoughException;
import com.tpadsz.exception.CheckNotAllowedException;
import com.tpadsz.exception.MemcachedNotResponsedException;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.exception.SystemAlgorithmException;

public interface GoodsService {

	List<DGoods> getGoodsByShelf(String sid, int pageNo, int pageSize) throws NotExecutedDbException;

	DGoods getCompleteGoodsById(String gid) throws NotExecutedDbException;

	DOrder buyGoods(String uid, String gid, String check, RExpressPost express) throws GoodsNotFoundException, CoinsNotEnoughException, CheckNotAllowedException, GoodsNotEnoughException, SystemAlgorithmException, MemcachedNotResponsedException;

	DOrder buyGoodsTemp(String uid, String gid, String check, RExpressPost express) throws GoodsNotFoundException, CoinsNotEnoughException, CheckNotAllowedException, GoodsNotEnoughException, SystemAlgorithmException, MemcachedNotResponsedException;
	
	DGoods getSimpleGoodsById(String gid) throws NotExecutedDbException;

	void updateStockAndSales();

	DGoods getGoods(String gid) throws GoodsNotFoundException, SystemAlgorithmException, MemcachedNotResponsedException;

}
