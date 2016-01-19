package com.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.OfferFactory;
import com.model.dd.ResultDict;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.TokenManager;
import com.tpadsz.uic.user.api.exception.TokenLockedNotEffectiveException;
import com.tpadsz.uic.user.api.exception.TokenNotEffectiveException;
import com.tpadsz.uic.user.api.exception.TokenReplacedException;
import com.tpadsz.uic.user.api.exception.TokenUnlockedNotEffectiveException;
import com.utils.Encryption;
import com.utils.HttpServletUtil;
import com.utils.LoggerUtils;

public class UserTokenValidationInterceptor extends HandlerInterceptorAdapter{
//	private Logger logger = LoggerUtils.system;
	private TokenManager tokenManager;
	private Logger logger = LoggerUtils.SYSTEM;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		JSONObject params = (JSONObject) request.getAttribute("decodedParams");
		String token = params.getString("token");
		String uid = params.getString("uid");
		Map<String, String> data = new HashMap<>();
		if(StringUtils.isNotBlank(uid)){
			if(StringUtils.isBlank(token)){
				data.put("result", ResultDict.TOKEN_NOT_SUBMIT.getCode());
				HttpServletUtil.renderText(response, Encryption.encode(JSONObject.toJSONString(data)));
				return false;
			}else {
				try {
					tokenManager.verifyToken(OfferFactory.generateLoginedOffer(uid, token));
					return true;
				}catch(TokenReplacedException e){
					data.put("result", ResultDict.TOKEN_REPLACED.getCode());
				}catch(TokenLockedNotEffectiveException e){
					data.put("result", ResultDict.TOKEN_LOCKED_UNEFFECTIVE.getCode());
				}catch(TokenUnlockedNotEffectiveException e){
					data.put("result", ResultDict.TOKEN_UNLOCKED_UNEFFECTIVE.getCode());
				}catch (TokenNotEffectiveException e) {
					data.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
				} catch(SystemAlgorithmException e){
					data.put("result", ResultDict.SYSTEM_ERROR.getCode());
					logger.error("bean:userTokenValidationInterceptor, method:preHandle", e);
				} catch (Exception e) {
					data.put("result", ResultDict.SYSTEM_ERROR.getCode());
					logger.error("严重 :: ", e);
				}
				HttpServletUtil.renderText(response, Encryption.encode(JSONObject.toJSONString(data)));
				return false;
			}
		}else {
			return false;
		}
		
	}

	@Autowired
	public void setTokenManager(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}


}
