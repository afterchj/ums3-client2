package com.service.impl;

import java.util.Set;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.MemcachedObjectType;
import com.google.common.collect.Sets;
import com.service.CountService;
import com.service.UserService;
import com.tpadsz.uic.user.api.vo.Firmware;
import com.utils.Constants;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private MemcachedClient memcachedClient;
	private CountService countService;
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Override
	public Long searchUserVisitors(String uid) {
		Long visitors = null;
		try {
			visitors = memcachedClient.get(MemcachedObjectType.COUNTER_USER_VISITOR.getPrefix() + uid);
			if(visitors == null || visitors < 0){
				visitors = countService.countUserVisitors(uid);
				if(visitors != null){
					memcachedClient.set(MemcachedObjectType.COUNTER_USER_VISITOR.getPrefix() + uid, MemcachedObjectType.COUNTER_USER_VISITOR.getExpiredTime(), visitors);
				}
			}
		} catch (Exception e) {
			logger.error("bean:userService, method:searchUserVisitors", e);
		}
		return visitors;
	}
	
	@Override
	public Integer selectModeByFirmware(final Firmware firmware) {
		if(firmware == null || StringUtils.isBlank(firmware.getClientVersion()) || StringUtils.isBlank(firmware.getFm()) || StringUtils.isBlank(firmware.getImei())){
			return Constants.ENABLED;
		}
		String key = String.format(MemcachedObjectType.CACHE_FILTERED_USER.getPrefix(), firmware.getFm(), firmware.getClientVersion());
		String numKey = String.format(MemcachedObjectType.CACHE_FILTERED_USER_NUM.getPrefix(), firmware.getFm(), firmware.getClientVersion());
		Integer status = Constants.DISABLED;
		try {
			Integer userNum = memcachedClient.get(numKey);
			if(userNum != null && userNum >= 100){
				return Constants.ENABLED;
			}
			synchronized (this) {
				if(userNum == null){
					memcachedClient.set(numKey, MemcachedObjectType.CACHE_FILTERED_USER_NUM.getExpiredTime(), 1);
					memcachedClient.set(key, MemcachedObjectType.CACHE_FILTERED_USER.getExpiredTime(), Sets.newHashSet(firmware.getImei()));
				}else {
					GetsResponse<Set<String>> response = memcachedClient.gets(key);
					Set<String> prohibitedImeis = response == null ? null : response.getValue();
					if(CollectionUtils.isNotEmpty(prohibitedImeis) && !prohibitedImeis.contains(firmware.getImei())){
						memcachedClient.cas(key, MemcachedObjectType.CACHE_FILTERED_USER.getExpiredTime(), new CASOperation<Set<String>>() {

							@Override
							public int getMaxTries() {
								return 3;
							}

							@Override
							public Set<String> getNewValue(long currentCAS,	Set<String> currentValue) {
								currentValue.add(firmware.getImei());
								return currentValue;
							}
						});
						memcachedClient.cas(numKey, MemcachedObjectType.CACHE_FILTERED_USER_NUM.getExpiredTime(), new CASOperation<Integer>() {

							@Override
							public int getMaxTries() {
								return 3;
							}

							@Override
							public Integer getNewValue(long currentCAS, Integer currentValue) {
								return currentValue + 1;
							}
						});
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("bean:userService, method:selectModeByFirmware \tFirmware:" + ToStringBuilder.reflectionToString(firmware), e);
		}
		return status;
	}


	@Autowired
	public void setCountService(CountService countService) {
		this.countService = countService;
	}

	@Autowired
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

}
