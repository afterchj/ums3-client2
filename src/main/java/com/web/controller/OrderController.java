//package com.web.controller;
//
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mapper.BeanMapper;
//import com.model.dd.ResultDict;
//import com.model.trade.DOrder;
//import com.service.trade.OrderService;
//import com.tpadsz.exception.NotExecutedDbException;
//import com.web.vo.Order;
//
//@Controller("orderController")
//@RequestMapping("/order")
//public class OrderController extends BaseDecodedController{
//
//	private OrderService orderService;
//	public static final int PAGE_SIZE = 10;
//
//	private List<Order> convert(List<DOrder> orders) {
//		if(CollectionUtils.isEmpty(orders)){
//			return null;
//		}
//		return BeanMapper.mapList(orders, Order.class);
//	}
//
//	@RequestMapping(value="/list/p_{pageNo}", method=RequestMethod.POST)
//	public String shelfGoods(@ModelAttribute("decodedParams")JSONObject params, @PathVariable("pageNo")Integer pageNo, ModelMap model){
//		try {
//			String uid = params.getString("uid");
//			List<DOrder> orders = orderService.getByUser(uid, pageNo, PAGE_SIZE);
//			model.put("orders", convert(orders));
//			if(CollectionUtils.isEmpty(orders) || orders.size() < OrderController.PAGE_SIZE){
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
//
//	@Autowired
//	public void setOrderService(OrderService orderService) {
//		this.orderService = orderService;
//	}
//
//}
