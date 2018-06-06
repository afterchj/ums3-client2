package com.cache;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.Transcoder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;

import com.tpadsz.exception.MemcachedNotResponsedException;
import com.utils.LoggerUtils;

public class XMemcachedClient implements DisposableBean {

	private static Logger logger = LoggerUtils.SYSTEM;

	private MemcachedClient memcachedClient;
	private long optTimeout = 1000;

	public <T> T get(String key) throws MemcachedNotResponsedException {
		try {
			return memcachedClient.get(key, optTimeout);
		} catch (RuntimeException | TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
		}
		return null;
	}
	
	/**
	 * Just like get,But it return a GetsResponse,include cas value for cas
	 * update.
	 * 
	 * @param <T>
	 * @param key
	 *            key
	 * @param timeout
	 *            operation timeout
	 * @param transcoder
	 * 
	 * @return GetsResponse
	 * @throws MemcachedNotResponsedException 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> GetsResponse<T> gets(final String key, final Transcoder<T> transcoder) throws MemcachedNotResponsedException {
		try {
			return memcachedClient.gets(key, optTimeout, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
			return null;
		}
	}

	/**
	 * @see #gets(String, long, Transcoder)
	 * @param <T>
	 * @param key
	 * @return
	 * @throws MemcachedNotResponsedException 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> GetsResponse<T> gets(final String key) throws MemcachedNotResponsedException {
		try {
			return memcachedClient.gets(key, optTimeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
			return null;
		}
	}

	public Map<String, Object> getBulk(List<String> keys) throws MemcachedNotResponsedException {
		try {
			return memcachedClient.get(keys, optTimeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, String.valueOf(keys));
			return null;
		}
	}

	public void set(String key, int expiredTime, Object value) throws MemcachedNotResponsedException {
		try {
			memcachedClient.set(key, expiredTime, value);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
		}
	}

	public void delete(String key) throws MemcachedNotResponsedException {
		try {
			memcachedClient.delete(key, optTimeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
		}
	}

	public long incr(String key, int by, long defaultValue) throws MemcachedNotResponsedException {
		try {
			return memcachedClient.incr(key, by, defaultValue);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
			return -1;
		}
	}
	
	public long incr(String key, int by, long defaultValue, int exp) throws MemcachedNotResponsedException {
		try {
			return memcachedClient.incr(key, by, defaultValue, exp);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
			return -1;
		}
	}

	public long decr(String key, int by, long defaultValue) throws MemcachedNotResponsedException {
		try {
			return memcachedClient.decr(key, by, defaultValue);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
			return -1;
		}
	}
	
	/**
	 * Cas is a check and set operation which means "store this data but only if
	 * no one else has updated since I last fetched it."
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @param timeout
	 * @param cas
	 * @return
	 * @throws MemcachedNotResponsedException 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final int exp, final T value,
			final Transcoder<T> transcoder, final long cas) throws MemcachedNotResponsedException{
		try {
			return memcachedClient.cas(key, exp, value, transcoder, optTimeout, cas);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
			return false;
		}
	}

	/**
	 * @see #cas(String, int, Object, Transcoder, long, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param timeout
	 * @param cas
	 * @return
	 * @throws MemcachedNotResponsedException 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean cas(final String key, final int exp, final Object value, final long cas) throws MemcachedNotResponsedException {
		try {
			return memcachedClient.cas(key, exp, value, optTimeout, cas);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
			return false;
		}
	}

	/**
	 * Cas is a check and set operation which means "store this data but only if
	 * no one else has updated since I last fetched it."
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param operation
	 *            CASOperation
	 * @param transcoder
	 *            object transcoder
	 * @return
	 * @throws MemcachedNotResponsedException 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final int exp,
			final CASOperation<T> operation, final Transcoder<T> transcoder) throws MemcachedNotResponsedException{
		try {
			return memcachedClient.cas(key, exp, operation, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e, key);
			return false;
		}
	}

	private void handleException(Exception e, String key) throws MemcachedNotResponsedException {
		throw new MemcachedNotResponsedException("xmemcached client receive an exception with key:" + key + "  " + e.getMessage());
	}

	public void destroy() {
		if (memcachedClient != null) {
			try {
				memcachedClient.shutdown();
			} catch (IOException e) {
				logger.error("memcached client receive an exception while shutting down.", e);
			}
		}
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	
	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setOptTimeout(long optTimeout) {
		this.optTimeout = optTimeout;
	}

}
