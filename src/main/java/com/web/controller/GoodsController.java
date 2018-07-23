//package com.web.controller;
//
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.mapper.BeanMapper;
//import com.model.dd.ResultDict;
//import com.model.trade.DGoods;
//import com.model.trade.DShelf;
//import com.service.trade.GoodsService;
//import com.service.trade.ShelfService;
//import com.tpadsz.exception.NotExecutedDbException;
//import com.utils.Constants;
//import com.utils.LoggerUtils;
//import com.web.vo.Goods;
//import com.web.vo.GoodsShelf;
//
//@Controller("goodsController")
//@RequestMapping("/goods")
//public class GoodsController extends BaseController{
//
//	private Logger system = LoggerUtils.SYSTEM;
//	public static final int PAGE_SIZE = 20;
//	private GoodsService goodsService;
//	private ShelfService shelfService;
//
//	private List<GoodsShelf> convertShelf(List<DShelf> shelvies) {
//		return BeanMapper.mapList(shelvies, GoodsShelf.class);
//	}
//
//	private List<Goods> convert(List<DGoods> goodses) {
//		if(CollectionUtils.isEmpty(goodses)){
//			return null;
//		}
//		return BeanMapper.mapList(goodses, Goods.class);
//	}
//
//	@RequestMapping(value="/list", method=RequestMethod.GET)
//	public String list(ModelMap model){
//		try {
//			List<GoodsShelf> shelvies = convertShelf(shelfService.getAll(Constants.ENABLED));
//			model.put("shelvies", shelvies);
//			if(CollectionUtils.isNotEmpty(shelvies)){
//				List<DGoods> goods = goodsService.getGoodsByShelf(shelvies.get(0).getId(), 1, PAGE_SIZE);
//				model.put("goods", convert(goods));
//				if(CollectionUtils.isEmpty(goods) || goods.size() < GoodsController.PAGE_SIZE){
//					model.put("pStatus", "0");
//				}else {
//					model.put("pStatus", "1");
//				}
//			}
//			model.put("result", ResultDict.SUCCESS.getCode());
//		} catch (NotExecutedDbException e) {
//			system.error(e);
//			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
//		}
//		return null;
//	}
//
//	@RequestMapping(value="/sid_{sid}/p_{pageNo}", method=RequestMethod.GET)
//	public String shelfGoods(@PathVariable("sid") String sid, @PathVariable("pageNo")Integer pageNo, ModelMap model){
//		try {
//			List<DGoods> goods = goodsService.getGoodsByShelf(sid, pageNo, PAGE_SIZE);
//			model.put("goods", goods);
//			if(CollectionUtils.isEmpty(goods) || goods.size() < GoodsController.PAGE_SIZE){
//				model.put("pStatus", "0");
//			}else {
//				model.put("pStatus", "1");
//			}
//			model.put("result", ResultDict.SUCCESS.getCode());
//		} catch (NotExecutedDbException e) {
//			system.error(e);
//			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
//		}
//		return null;
//	}
//
//	@RequestMapping(value="/id_{gid}", method=RequestMethod.GET)
//	public String goodsDetail(@PathVariable("gid") String gid, ModelMap model){
//		try {
//			DGoods goods = goodsService.getCompleteGoodsById(gid);
//			model.put("goods", goods);
//			model.put("result", ResultDict.SUCCESS.getCode());
//		} catch (NotExecutedDbException e) {
//			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
//			system.error(e);
//		}
//		return null;
//	}
//
//	@Autowired
//	public void setGoodsService(GoodsService goodsService) {
//		this.goodsService = goodsService;
//	}
//
//	@Autowired
//	public void setShelfService(ShelfService shelfService) {
//		this.shelfService = shelfService;
//	}
//
//}
