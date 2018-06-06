package com.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.model.CateItem;
import com.model.dd.OfferFactory;
import com.service.CateItemService;
import com.service.CoinService;
import com.tpadsz.cic.coin.api.CoinsAccountManager;
import com.tpadsz.cic.coin.vo.CoinsEarnedType;
import com.utils.Constants;
import com.web.vo.CoinVo;

@Service("coinService")
public class CoinServiceImpl implements CoinService{
	
	private CoinsAccountManager coinsAccountManager;
	private CateItemService cateItemService;
	private Logger logger = Logger.getLogger(CoinServiceImpl.class);
	
	@Override
	public List<CoinVo> findCoinMap(String uid) {
		List<CoinVo> coins = null;
		try {
			List<CateItem> cates = cateItemService.getAll("coin");
			if(CollectionUtils.isEmpty(cates)){
				return null;
			}
			List<CoinsEarnedType> taskIds = getCateIdStrings(cates);
			Map<Integer, Integer> cateCoinMap = coinsAccountManager.findCoinsEarnedDetailsByType(OfferFactory.generateCoinsAccountOffer(uid, Constants.APP_ID), taskIds.toArray(new CoinsEarnedType[taskIds.size()]));
			if(cateCoinMap == null){
				cateCoinMap = Maps.newHashMap();
			}
			coins = mergeCoin(cates, cateCoinMap);
		} catch (Exception e) {
			logger.error("bean:coinService, method:findCoinMap", e);
		}
		return coins;
	}
	
//	private List<String> getCateIdStrings2(List<CateItem> cates) {
//		List<String> ids = Lists.newArrayList();
//		if(CollectionUtils.isNotEmpty(cates)){
//			for(CateItem cate : cates){
//				ids.add(String.valueOf(cate.getId()));
//			}
//		}
//		return ids;
//	}
	
	private List<CoinsEarnedType> getCateIdStrings(List<CateItem> cates) {
		List<CoinsEarnedType> ids = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(cates)){
			for(CateItem cate : cates){
				try {
					CoinsEarnedType type = CoinsEarnedType.parse(cate.getId().intValue());
					if(type != null){
						ids.add(type);
					}
				} catch (NumberFormatException e) {
					logger.error("bean:CoinsEarnedType, method:parse, categoryForm:" + JSONObject.toJSONString(cate), e);
				}
			}
		}
		return ids;
	}

	@Override
	public List<CoinVo> generateNewCoins() {
		List<CateItem> cates = cateItemService.getAll("coin");
		if(CollectionUtils.isEmpty(cates)){
			return null;
		}
		List<CoinVo> coins = Lists.newArrayList();
		for(CateItem cate : cates){
			coins.add(new CoinVo(cate.getName(), 0));
		}
		return coins;
	}
	
	private List<CoinVo> mergeCoin(List<CateItem> cates,
			Map<Integer, Integer> cateCoinMap) {
		List<CoinVo> coins = Lists.newArrayList();
		for(CateItem categoryForm : cates){
			try {
				Integer num = cateCoinMap.get(categoryForm.getId().intValue());
				if(num == null){
					num = 0;
				}
				CoinVo coin = new CoinVo(categoryForm.getName(), num);
				coins.add(coin);
			} catch (NumberFormatException e) {
				logger.error("bean:cateCoinMap,method:get, categoryForm:" + JSONObject.toJSONString(categoryForm), e);
			}
		}
		return coins;
	}
	
//	private List<CoinVo> mergeCoin2(List<CateItem> cates,
//			Map<String, Integer> cateCoinMap) {
//		List<CoinVo> coins = Lists.newArrayList();
//		for(CateItem cateItem : cates){
//			Integer num = cateCoinMap.get(String.valueOf(cateItem.getId()));
//			if(num == null){
//				num = 0;
//			}
//			CoinVo coin = new CoinVo(cateItem.getName(), num);
//			coins.add(coin);
//		}
//		return coins;
//	}

	@Autowired
	public void setCateItemService(CateItemService cateItemService) {
		this.cateItemService = cateItemService;
	}

	@Autowired
	public void setCoinsAccountManager(CoinsAccountManager coinsAccountManager) {
		this.coinsAccountManager = coinsAccountManager;
	}
}
