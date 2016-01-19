package com.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.ClientFileService;
import com.web.vo.ClientFileVo;

@RequestMapping("/app/dd")
@Controller("fileDownloadConstroller")
public class FileDownloadController {

	private Logger logger = Logger.getLogger(FileDownloadController.class);
	private ClientFileService clientFileService;
	
	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException re,
			HttpServletRequest request, ModelMap model) {
		logger.error("FileDownloadController .", re);
		model.put("status", "500");
		return null;
	}
	
	@RequestMapping("/client")
	public String getClient(ModelMap model) throws Exception {
//		String version = request.getParameter(Constants.PARA_CLIENT_VERSION);
		String newestVersion = clientFileService.getNewestVersionCode();
		if(StringUtils.isBlank(newestVersion)){
			logger.error("cannot find newestVersion.");
			model.put("status", "100");
			return null;
		}
		ClientFileVo client = clientFileService.getClientByVersion(newestVersion);
		if (client == null) {
			logger.error(String.format("版本号: %s 不存在", newestVersion));
			model.put("status", "200");
			return null;
		}
		model.put("status", "000");
		model.put("client", client);
		return null;
	}
	
	@RequestMapping("/share")
	public String getClientByShare(ModelMap model, HttpServletResponse response) throws Exception {
		String newestVersion = clientFileService.getNewestVersionCode();
		if(StringUtils.isBlank(newestVersion)){
			logger.error("cannot find newestVersion.");
			return null;
		}
		ClientFileVo client = clientFileService.getClientByVersion(newestVersion);
		if (client == null) {
			logger.error(String.format("版本号: %s 不存在", newestVersion));
			return null;
		}
		response.sendRedirect(client.getPath());
		return null;
	}

	@Autowired
	public void setClientFileService(ClientFileService clientFileService) {
		this.clientFileService = clientFileService;
	}

}
