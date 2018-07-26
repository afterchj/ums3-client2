package com.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.ResultDict;
import com.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
//	private NamedThreadLocal<Long> startTimeLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		long beginTime = System.currentTimeMillis();//开始时间
//		startTimeLocal.set(beginTime);//线程绑定变量(该数据只有当前请求的线程可见)
		String params = request.getParameter("params");
		JSONObject paramMap = new JSONObject();
		if(StringUtils.isNotBlank(params)){
			try {
				String decodedParams = new String(DESCoder.decrypt(Encodes.decodeBase64(URLDecoder.decode(params, "utf-8"))), "utf-8");
				paramMap = JSONObject.parseObject(decodedParams);
				request.setAttribute("decodedParams", paramMap);
				return true;
			} catch (Exception e) {
				system.error("ParamsDecoderFilter decode Params error! Handler:" + ToStringBuilder.reflectionToString(handler));
				Map<String, String> data = new HashMap<>();
				data.put("result", ResultDict.PARAMS_NOT_PARSED.getCode());
				HttpServletUtil.renderText(response, Encryption.encode(JSONObject.toJSONString(data)));
				return false;
			}
		}else {
			String result = ResultDict.PARAMS_BLANK.getCode();
//			String ip = request.getHeader("x-forwarded-for").split(",")[0];
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
//		        String ip = "127.0.0.1";
//		String ip = request.getHeader("x-forwarded-for").split(",")[0];
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
//	private void logAccess(Object params, String resultCode, Object value, String uri, String ip) {
//		JSONObject jsonObj = new JSONObject();
//		jsonObj.put("params", params);
//		jsonObj.put("uri", uri);
//		jsonObj.put("result", resultCode);
//		jsonObj.put("ip", ip);
//		daylog.warn(jsonObj.toJSONString());
//	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		long endTime = System.currentTimeMillis();//结束时间
//		long beginTime = startTimeLocal.get();//得到线程绑定的局部变量(开始时间)
//		long consumeTime = endTime - beginTime;
//		if (consumeTime >5000){
//			System.out.println(String.format("%s consume %d millis", request.getRequestURI(),consumeTime));
//		}
	}

}
