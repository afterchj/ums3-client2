///*
//package com.web.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.alibaba.fastjson.JSONObject;
//import com.model.dd.OfferFactory;
//import com.model.dd.ResultDict;
//import com.service.CoinService;
//import com.tpadsz.cic.coin.api.CoinsAccountManager;
//import com.utils.Constants;
//import com.web.vo.CoinVo;
//
//@Controller("coinController")
//@RequestMapping("/coin")
//public class CoinController extends BaseDecodedController{
//
//	private CoinService coinService;
//	private CoinsAccountManager coinsAccountManager;
//
//	@RequestMapping(value="/search", method=RequestMethod.POST)
//	public String searchAll(@ModelAttribute("decodedParams")JSONObject params, ModelMap model){
//		try {
//			String uid = params.getString("uid");
//			model.put("avail", coinsAccountManager.findAvail(OfferFactory.generateCoinsAccountOffer(uid, Constants.APP_ID)));
//			model.put("history", coinsAccountManager.findEarnedIncome(OfferFactory.generateCoinsAccountOffer(uid, Constants.APP_ID)));
//			List<CoinVo> coins = coinService.findCoinMap(uid);
//			model.put("details", coins);
//			model.put("result", ResultDict.SUCCESS.getCode());
//		} catch (Exception e) {
//			system.error("method:searchAll", e);
//			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
//		}
//		return null;
//	}
//
//	@RequestMapping(value="/search/avail", method=RequestMethod.POST)
//	public String searchAvail(@ModelAttribute("decodedParams")JSONObject params, ModelMap model){
//		try {
//			String uid = params.getString("uid");
//			model.put("avail", coinsAccountManager.findAvail(OfferFactory.generateCoinsAccountOffer(uid, Constants.APP_ID)));
//			model.put("coinsToday", coinsAccountManager.findDailyEarnedCoins(OfferFactory.generateCoinsAccountOffer(uid, Constants.APP_ID)));
//			model.put("history", coinsAccountManager.findEarnedIncome(OfferFactory.generateCoinsAccountOffer(uid, Constants.APP_ID)));
//			model.put("result", ResultDict.SUCCESS.getCode());
//		} catch (Exception e) {
//			system.error("method:seachAvail", e);
//			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
//		}
//		return null;
//	}
//
//
//	@Autowired
//	public void setCoinService(CoinService coinService) {
//		this.coinService = coinService;
//	}
//
//	@Autowired
//	public void setCoinsAccountManager(CoinsAccountManager coinsAccountManager) {
//		this.coinsAccountManager = coinsAccountManager;
//	}
//
//
//
//}
//*/
