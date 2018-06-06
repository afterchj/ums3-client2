package com.service.trade.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.MemcachedObjectType;
import com.dao.trade.GoodsDao;
import com.exception.GoodsNotEnoughException;
import com.exception.GoodsNotFoundException;
import com.google.common.collect.Lists;
import com.mapper.BeanMapper;
import com.model.trade.DGoods;
import com.model.trade.DOrder;
import com.model.trade.RExpressPost;
import com.service.trade.GoodsService;
import com.service.trade.OrderService;
import com.tpadsz.cic.coin.api.CoinsConsumerManager;
import com.tpadsz.cic.coin.api.CoinsEarnerManager;
import com.tpadsz.cic.coin.exception.CoinsNotEnoughException;
import com.tpadsz.cic.coin.vo.CoinsConsumedType;
import com.tpadsz.cic.coin.vo.CoinsConsumerOffer;
import com.tpadsz.cic.coin.vo.CoinsEarnedType;
import com.tpadsz.cic.coin.vo.CoinsEarnerOffer;
import com.tpadsz.cic.coin.vo.CoinsOrder;
import com.tpadsz.exception.CheckNotAllowedException;
import com.tpadsz.exception.MemcachedNotResponsedException;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.utils.Constants;
import com.utils.LoggerUtils;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

	private GoodsDao goodsDao;
//	private DeductManager coinDeductManager;
//	private AddManager coinAddManager;
	private CoinsConsumerManager coinsConsumeManager;
	private CoinsEarnerManager coinsEarnerManager;
	private OrderService orderService;
	private Logger logger = Logger.getLogger(GoodsServiceImpl.class);
	private Logger dayLogger = LoggerUtils.DAYLOG;
	private Map<String, DGoods> goodsMap = new ConcurrentHashMap<String, DGoods>();
	private MemcachedClient memcachedClient;
	
	@Override
	public void updateStockAndSales(){
		dayLogger.warn("updateStockAndSales start.");
		for(DGoods goods : goodsMap.values()){
			try {
				String stockKey = String.format(MemcachedObjectType.COUNTER_GOODS_STOCK.getPrefix(), goods.getId());
				String salesKey = String.format(MemcachedObjectType.COUNTER_GOODS_SALES.getPrefix(), goods.getId());
				Integer stock = memcachedClient.get(stockKey);
				Integer sales = memcachedClient.get(salesKey);
				if(stock != null){
					goods.setStock(stock);
				}
				if(sales != null){
					goods.setSales(sales);
				}
				if(stock != null || sales != null){
					update(goods);
				}
			} catch (Exception e) {
				logger.error("updateStockAndSales error.", e);
			}
		}
		goodsMap.clear();
		dayLogger.warn("updateStockAndSales end.");
	}
	
	private void getStockAndSales(List<DGoods> goodses) {
		if(CollectionUtils.isNotEmpty(goodses)){
			for(DGoods goods : goodses){
				try {
					String stockKey = String.format(MemcachedObjectType.COUNTER_GOODS_STOCK.getPrefix(), goods.getId());
					String salesKey = String.format(MemcachedObjectType.COUNTER_GOODS_SALES.getPrefix(), goods.getId());
					Integer stock = memcachedClient.get(stockKey);
					Integer sales = memcachedClient.get(salesKey);
					if(stock != null){
						goods.setStock(stock);
					}
					if(sales != null){
						goods.setSales(sales);
					}
				} catch (TimeoutException | InterruptedException | MemcachedException e) {
					logger.error(e);
				}
			}
		}
	}

	public List<DGoods> getGoodsByShelf(String sid, int pageNo, int pageSize)
			throws NotExecutedDbException {
		List<DGoods> goods = Lists.newArrayList();
		try {
			goods = goodsDao.getByShelf(sid, (pageNo - 1) * pageSize, pageSize);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:goodsDao, method:getByShelf", e);
		}
		getStockAndSales(goods);
		return goods;
	}


	public DGoods getCompleteGoodsById(String gid) throws NotExecutedDbException {
		DGoods goods = null;
		try {
			goods = goodsDao.getCompleteGoodsById(gid);
			if(goods.getStock() < 0){
				goods.setStock(0);
			}
		} catch (Exception e) {
			throw new NotExecutedDbException(
					"bean:goodsDao, method:getAllById", e);
		}
		return goods;
	}
	
	@Override
	public DGoods getSimpleGoodsById(String gid) throws NotExecutedDbException {
		DGoods goods = null;
		try {
			goods = goodsDao.getSimpleGoodsById(gid);
			if(goods.getStock() < 0){
				goods.setStock(0);
			}
		} catch (Exception e) {
			throw new NotExecutedDbException(
					"bean:goodsDao, method:getAllById", e);
		}
		return goods;
	}
	
	public void deductStock(DGoods goods) throws GoodsNotFoundException, MemcachedNotResponsedException, GoodsNotEnoughException{
		String stockKey = String.format(MemcachedObjectType.COUNTER_GOODS_STOCK.getPrefix(), goods.getId());
		String salesKey = String.format(MemcachedObjectType.COUNTER_GOODS_SALES.getPrefix(), goods.getId());
		try {
			int times = 0;
			boolean succ = false;
			do{
				times ++;
				try {
					GetsResponse<Integer> response = memcachedClient.gets(stockKey);
					if(response.getValue() <= 0){
						throw new GoodsNotEnoughException("name:" + goods.getName() + ", id:" + goods.getId());
					}else {
						succ = memcachedClient.cas(stockKey, MemcachedObjectType.COUNTER_GOODS_STOCK.getExpiredTime(), response.getValue() - 1, response.getCas());
					}
				} catch (TimeoutException | InterruptedException | MemcachedException e) {
					logger.error(e);
				}
			}while(times < 3 && !succ);
			if(!succ){
				throw new MemcachedNotResponsedException("bean:memcachedClient, method:cas 3 times out");
			}
			memcachedClient.cas(salesKey, MemcachedObjectType.COUNTER_GOODS_SALES.getExpiredTime(), new CASOperation<Integer>() {

				@Override
				public int getMaxTries() {
					return 3;
				}

				@Override
				public Integer getNewValue(long currentCAS, Integer currentValue) {
					return currentValue + 1;
				}
			});
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			throw new MemcachedNotResponsedException("bean:memcachedClient, method:cas", e);
		}
	}
	
	public void addStock(DGoods goods) throws MemcachedNotResponsedException{
		String stockKey = String.format(MemcachedObjectType.COUNTER_GOODS_STOCK.getPrefix(), goods.getId());
		String salesKey = String.format(MemcachedObjectType.COUNTER_GOODS_SALES.getPrefix(), goods.getId());
		try {
			int times = 0;
			boolean succ = false;
			do{
				times ++;
				try {
					GetsResponse<Integer> response = memcachedClient.gets(stockKey);
					succ = memcachedClient.cas(stockKey, MemcachedObjectType.COUNTER_GOODS_STOCK.getExpiredTime(), response.getValue() + 1, response.getCas());
				} catch (TimeoutException | InterruptedException | MemcachedException e) {
					logger.error(e);
				}
			}while(times < 3 && !succ);
			if(!succ){
				throw new MemcachedNotResponsedException("bean:memcachedClient, method:cas 3 times out");
			}
			memcachedClient.cas(salesKey, MemcachedObjectType.COUNTER_GOODS_SALES.getExpiredTime(), new CASOperation<Integer>() {

				@Override
				public int getMaxTries() {
					return 3;
				}

				@Override
				public Integer getNewValue(long currentCAS, Integer currentValue) {
					return currentValue - 1;
				}
			});
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			throw new MemcachedNotResponsedException("bean:memcachedClient, method:cas", e);
		}
	}
	
	@Override
	public DGoods getGoods(String gid) throws GoodsNotFoundException, MemcachedNotResponsedException, SystemAlgorithmException{
		DGoods goods = goodsMap.get(gid);
		if(goods == null){
			try {
				goods = getSimpleGoodsById(gid);
			} catch (NotExecutedDbException e) {
				throw new SystemAlgorithmException("bean:goodsDao, method:getSimpleGoodsById", e);
			}
			if(goods == null){
				throw new GoodsNotFoundException();
			}
			String stockKey = String.format(MemcachedObjectType.COUNTER_GOODS_STOCK.getPrefix(), goods.getId());
			String salesKey = String.format(MemcachedObjectType.COUNTER_GOODS_SALES.getPrefix(), goods.getId());
			try {
				memcachedClient.set(stockKey, MemcachedObjectType.COUNTER_GOODS_STOCK.getExpiredTime(), goods.getStock());
				memcachedClient.set(salesKey, MemcachedObjectType.COUNTER_GOODS_SALES.getExpiredTime(), goods.getSales());
			} catch (TimeoutException | InterruptedException | MemcachedException e) {
				throw new MemcachedNotResponsedException("bean:memcachedClient, method:set", e);
			}
			goodsMap.put(gid, goods);
		}
		return goods;
	}

	private void update(DGoods goods) throws NotExecutedDbException {
		try {
			goodsDao.updateStockAndSales(goods);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:goodsDao, method:update", e);
		}
	}

	public DOrder buyGoods(String uid, String gid, String check, RExpressPost express)
			throws GoodsNotFoundException, CoinsNotEnoughException, CheckNotAllowedException, GoodsNotEnoughException, SystemAlgorithmException, MemcachedNotResponsedException {
		DGoods goods = getGoods(gid);
		
		DOrder order = null;
		CoinsOrder coinsOrder = null;
		deductStock(goods);
		try {
			coinsOrder = coinsConsumeManager.consumeCoins(new CoinsConsumerOffer(Constants.APP_ID, uid, gid, goods.getPrice(), "ded" + check, CoinsConsumedType.GOODS));
		} catch (Exception e1) {
			try {
				addStock(goods);
			} catch (Exception e) {
				logger.error("严重： Goods：" + goods.getId() + " AddStock Failed.", e);
			}
			throw e1;
		}
		order = generateOrder(express, goods, uid);
		try {
			orderService.save(order);
		} catch (NotExecutedDbException e) {
			coinsConsumeManager.rollBack(new CoinsConsumerOffer(coinsOrder.getAppid(), uid, coinsOrder.getItemId(), coinsOrder.getAmount(), order.getId(), CoinsConsumedType.GOODS));
			addStock(goods);
			throw new SystemAlgorithmException("bean:goodsService, method:buyGoods", e);
		} 
		coinsEarnerManager.earnCoins(new CoinsEarnerOffer(Constants.APP_ID, uid, order.getId(), goods.getPayback(), order.getId(), CoinsEarnedType.BONUS));
		return order;
	}
	
	@Override
	public DOrder buyGoodsTemp(String uid, String gid, String check,
			RExpressPost express) throws GoodsNotFoundException,
			CoinsNotEnoughException, CheckNotAllowedException,
			GoodsNotEnoughException, SystemAlgorithmException,
			MemcachedNotResponsedException {
		CoinsOrder coinsOrder = null;
		DGoods goods = new DGoods();
		goods.setId(gid);
		switch (gid) {
		case "61":
			goods.setPrice(10000);
			goods.setName("100M流量包-上海联通（全国通用）");
			break;
		case "62":
			goods.setPrice(15000);
			goods.setName("200M流量包-上海联通（全国通用）");
			break;
		case "63":
			goods.setPrice(30000);
			goods.setName("500M流量包-上海联通（全国通用）");
			break;
		}
		try {
			coinsOrder = coinsConsumeManager.consumeCoins(new CoinsConsumerOffer(Constants.APP_ID, uid, gid, goods.getPrice(), "ded" + check, CoinsConsumedType.GOODS));
		} catch (Exception e1) {
			throw e1;
		}
		DOrder order = generateOrder(express, goods, uid);
		try {
			orderService.save(order);
		} catch (NotExecutedDbException e) {
			coinsConsumeManager.rollBack(new CoinsConsumerOffer(coinsOrder.getAppid(), uid, coinsOrder.getItemId(), coinsOrder.getAmount(), order.getId(), CoinsConsumedType.GOODS));
			throw new SystemAlgorithmException("bean:goodsService, method:buyGoods", e);
		} 
		return order;
	}
	

	private DOrder generateOrder(RExpressPost express, DGoods goods, String uid) {
		DOrder order = BeanMapper.map(express, DOrder.class);
		order.setPrice(goods.getPrice());
		order.setStatus(Constants.ORDER_DEALING);
		order.setName(goods.getName());
		order.setGoods(goods.getId());
		order.setUid(uid);
		return order;
	}

	@Autowired
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Autowired
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Autowired
	public void setCoinsConsumeManager(CoinsConsumerManager coinsConsumeManager) {
		this.coinsConsumeManager = coinsConsumeManager;
	}

	@Autowired
	public void setCoinsEarnerManager(CoinsEarnerManager coinsEarnerManager) {
		this.coinsEarnerManager = coinsEarnerManager;
	}

}
