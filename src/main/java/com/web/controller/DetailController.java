//package com.web.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.service.CateItemService;
//import com.service.CountService;
//import com.service.FileStoreInfoService;
//import com.utils.Constants;
//import com.web.vo.CateItemVo;
//import com.web.vo.DetailVo;
//
//@Controller("detailController")
//@RequestMapping("/app")
//public class DetailController {
//
//	private Logger logger = Logger.getLogger(DetailController.class);
//	private FileStoreInfoService fileStoreInfoService;
//	private CateItemService cateItemService;
//	private CountService countService;
//
//	@ExceptionHandler(RuntimeException.class)
//	public String handleException(RuntimeException re,
//			HttpServletRequest request) {
//		logger.error("detail. Message: " + re.getMessage() + " URI:" + request.getRequestURI());
////		if (request.getContextPath().indexOf("\\/m\\/") > 0) {
////			return "forword:/app/m/home";
////		} else {
////			return "forward:/app/f/home";
////		}
//		return null;
//	}
//
//	@RequestMapping("/{gender}/details/id{id}")
//	public String detail(@PathVariable("id") String id,
//			@PathVariable("gender") String gender, ModelMap model) {
//		if(StringUtils.isBlank(id) || StringUtils.equalsIgnoreCase(id, "null")){
//			return "details";
//		}
//		DetailVo detailVo = fileStoreInfoService.getDetailStoreInfo(id);
//		if(detailVo == null){
//			logger.warn(String.format("找不到指定id:%s的锁屏", id));
//			return null;
//		}
//		if(Constants.NORMAL_LOCK.equalsIgnoreCase(detailVo.getDtype())){
//			CateItemVo category = cateItemService.getByTheme(detailVo.getId());
//			List<DetailVo> shuffleFileStoreInfos = fileStoreInfoService.getShuffleFileStoreByCategory(category.getId(), detailVo, 3);
//			for (DetailVo vo : shuffleFileStoreInfos) {
//				vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));
//			}
//			model.put("shuffle", shuffleFileStoreInfos);
//		}
//		detailVo.setDownloadTimes(countService.countTotalDownload(detailVo.getTitle()));
//		model.put("current", detailVo);
//		return "details";
//	}
//
//	@Autowired
//	public void setFileStoreInfoService(
//			FileStoreInfoService fileStoreInfoService) {
//		this.fileStoreInfoService = fileStoreInfoService;
//	}
//
//	@Autowired
//	public void setCateItemService(CateItemService cateItemService) {
//		this.cateItemService = cateItemService;
//	}
//
//	@Autowired
//	public void setCountService(CountService countService) {
//		this.countService = countService;
//	}
//
//}
