package com.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.ResultDict;
import com.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ParamsEncryptInterceptor implements HandlerInterceptor{
	private Logger system = LoggerUtils.SYSTEM;
	private Logger daylog = LoggerUtils.DAYLOG;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String params = request.getParameter("params");
		JSONObject paramMap = new JSONObject();
		if(StringUtils.isNotBlank(params)){
			try {
				String decodedParams = new String(DESCoder.decrypt(Encodes.decodeBase64(URLDecoder.decode(params, "utf-8"))), "utf-8");
				paramMap = JSONObject.parseObject(decodedParams);
				request.setAttribute("decodedParams", paramMap);
				return true;
			} catch (Exception e) {
//				system.error("ParamsDecoderFilter decode Params error! Handler:" + ToStringBuilder.reflectionToString(handler));
				Map<String, String> data = new HashMap<>();
				data.put("result", ResultDict.PARAMS_NOT_PARSED.getCode());
				HttpServletUtil.renderText(response, Encryption.encode(JSONObject.toJSONString(data)));
				return false;
			}
		}else {
			String result = ResultDict.PARAMS_BLANK.getCode();
			logAccess(null, result, null, request.getRequestURI());
			Map<String, String> data = new HashMap<>();
			data.put("result", result);
			HttpServletUtil.renderText(response, Encryption.encode(JSONObject.toJSONString(data)));
			return false;
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Object paramMap = modelAndView.getModel().remove("decodedParams");
		logAccess(paramMap, String.valueOf(modelAndView.getModel().get("result")), modelAndView.getModel(), request.getRequestURI());
		modelAndView.getModel().put(Constants.BACKGROUND_ENCRPTION_KEY, true);
	}

	private void logAccess(Object params, String resultCode, Object value, String uri){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("params", params);
		jsonObj.put("uri", uri);
		jsonObj.put("result", resultCode);
	    //jsonObj.put("value", value);
		daylog.warn(jsonObj.toJSONString());
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
