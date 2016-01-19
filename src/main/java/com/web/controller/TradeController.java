package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.exception.GoodsNotEnoughException;
import com.exception.GoodsNotFoundException;
import com.mapper.BeanMapper;
import com.model.dd.ResultDict;
import com.model.trade.DGoods;
import com.model.trade.DOrder;
import com.model.trade.RExpressPost;
import com.service.trade.GoodsService;
import com.tpadsz.cic.coin.exception.CoinsNotEnoughException;
import com.tpadsz.exception.CheckNotAllowedException;
import com.tpadsz.exception.MemcachedNotResponsedException;
import com.tpadsz.exception.ParamParseException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.QueryManager;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.utils.Constants;
import com.web.vo.Order;

@Controller("tradeController")
@RequestMapping("/trade")
public class TradeController extends BaseDecodedController{

	private QueryManager queryManager;
	private GoodsService goodsService;
	
	private RExpressPost generateExpress(JSONObject params) throws ParamParseException {
		try {
			RExpressPost express = params.getObject("data", RExpressPost.class);
			return express;
		} catch (Exception e) {
			throw new ParamParseException();
		}
	}
	
	public static void main(String[] args) {
		String params = "{'data':{'mobile':'15051579760'},'uid':'c224264431fa4b2c93511c7f500079b6','firmware':{'os':'android-4.2.1','model':'M701','fm':'','netEnv':'WIFI','imei':'863135020568242','pkg':'com.change.unlock','operators':'YD','resolution':'720*1280','imsi':'460023515742111','clientVersion':'3.5.0'},'check':'bfa6f681-f859-4b23-8a52-d6b69bebe21e','token':'b792c6c6-b5ff-4cbe-abf2-4250e40b0a93'}";
		JSONObject decodedParams = JSONObject.parseObject(params);
		System.out.println(decodedParams.get("data"));
	}
	
	private Order convert(DOrder order) {
		if(order == null){
			return null;
		}
		Order vo = BeanMapper.map(order, Order.class);
		return vo;
	}

	@RequestMapping(value="/id_{gid}/buy", method=RequestMethod.POST)
	public String buyGoods(@ModelAttribute("decodedParams") JSONObject params, @PathVariable("gid") String gid, ModelMap model) {
		String uid = params.getString("uid");
		String check = params.getString("check");
		try {
			AppUser appUser = queryManager.findById(uid);
			if(appUser.getStatus() == Constants.DISABLED){
				model.put("result", ResultDict.AUTHORITY_NOT_ALLOWED.getCode());
				return null;
			}
			RExpressPost express = generateExpress(params);
			DOrder order = goodsService.buyGoods(uid, gid, check, express);
			DGoods goods = goodsService.getGoods(gid);
			model.put("payback", goods.getPayback());
			model.put("order", convert(order));
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (SystemAlgorithmException e) {
			system.error(e);
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		} catch (GoodsNotFoundException e) {
			model.put("result", ResultDict.ID_NOT_FOUND.getCode());
		} catch (CoinsNotEnoughException e) {
			model.put("result", ResultDict.COINS_NOT_ENOUGH.getCode());
		} catch (CheckNotAllowedException e) {
			model.put("result", ResultDict.CHECK_REPEATED.getCode());
		} catch (MemcachedNotResponsedException e) {
			model.put("result", ResultDict.TIMEOUT.getCode());
		} catch (GoodsNotEnoughException e) {
			model.put("result", ResultDict.GOODS_NOT_ENOUGH.getCode());
		} catch (ParamParseException e) {
			model.put("result", ResultDict.PARAMS_NOT_PARSED.getCode());
		} 
		return null;
	}
	
	@RequestMapping(value="/id_{gid}/buyUnicom", method=RequestMethod.POST)
	public String buyGoodsForUnicom(@ModelAttribute("decodedParams") JSONObject params, @PathVariable("gid") String gid, ModelMap model) {
		String uid = params.getString("uid");
		String check = params.getString("check");
		try {
			AppUser appUser = queryManager.findById(uid);
			if (appUser.getStatus() == Constants.DISABLED) {
				model.put("result", ResultDict.AUTHORITY_NOT_ALLOWED.getCode());
				return null;
			}
			RExpressPost express = generateExpress(params);
			DOrder order = goodsService.buyGoodsTemp(uid, gid, check, express);
			model.put("order", convert(order));
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (SystemAlgorithmException e) {
			system.error(e);
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		} catch (GoodsNotFoundException e) {
			model.put("result", ResultDict.ID_NOT_FOUND.getCode());
		} catch (CoinsNotEnoughException e) {
			model.put("result", ResultDict.COINS_NOT_ENOUGH.getCode());
		} catch (CheckNotAllowedException e) {
			model.put("result", ResultDict.CHECK_REPEATED.getCode());
		} catch (MemcachedNotResponsedException e) {
			model.put("result", ResultDict.TIMEOUT.getCode());
		} catch (GoodsNotEnoughException e) {
			model.put("result", ResultDict.GOODS_NOT_ENOUGH.getCode());
		} catch (ParamParseException e) {
			model.put("result", ResultDict.PARAMS_NOT_PARSED.getCode());
		} 
		return null;
	}

	@Autowired
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	@Autowired
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

}
