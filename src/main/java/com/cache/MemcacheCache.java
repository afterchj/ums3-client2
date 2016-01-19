package com.cache;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

import com.tpadsz.exception.MemcachedNotResponsedException;
import com.tpadsz.exception.ValidationException;
import com.utils.Constants;
import com.utils.LoggerUtils;
import com.utils.PropertiesLoader;

public class MemcacheCache implements Cache {

	private XMemcachedClient client;
	private String name;
	private Logger logger = LoggerUtils.SYSTEM;
	private PropertiesLoader properties = PropertiesLoader.getInstance();

	public MemcacheCache() {

	}

	public MemcacheCache(String name, XMemcachedClient client) {
		Assert.notNull(client, "Memcache client must not be null");
		this.client = client;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public Object getNativeCache() {
		return this.client;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object value = null;
		try {
			value = this.client.get(objectToString(key));
		} catch (MemcachedNotResponsedException | ValidationException e) {
			logger.error(e);
		}
		return (value != null ? new SimpleValueWrapper(value) : null);
	}

	@Override
	public void put(Object key, Object value) {
		try {
			if(value != null){
				this.client.set(objectToString(key), properties.getInteger("cache.memcached.timeout." + name, Constants.CACHE_MEMCACHED_TIMEOUT), value);
			}else {
				this.client.delete(objectToString(key));
			}
		} catch (MemcachedNotResponsedException | ValidationException e) {
			logger.error(e);
		}
	}

	@Override
	public void evict(Object key) {
		try {
			this.client.delete(objectToString(key));
		} catch (MemcachedNotResponsedException | ValidationException e) {
			logger.error(e);
		}
	}

	@Override
	public void clear() {
		// TODO delete all data
	}
	
	private String objectToString(Object object) throws ValidationException {
		if (object == null) {
			throw new ValidationException("key is null.");
		} else {
			return StringUtils.join(name, ":", object);
		}
	}
	
	
	public void setClient(XMemcachedClient client) {
		this.client = client;
	}

	public XMemcachedClient getClient() {
		return client;
	}

	public void setName(String name) {
		this.name = name;
	}

}
