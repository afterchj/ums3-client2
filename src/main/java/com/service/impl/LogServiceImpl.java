package com.service.impl;

import org.springframework.stereotype.Service;

import com.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService {

//	private Logger logger = Logger.getLogger(LogService.class);
//
//	private ExecutorService pool = Executors.newFixedThreadPool(6);
//	
//	private LogForStoreDao logForStoreDao;
//
//	private static final String TABLE_PREFIX = "log_f_store_";
//	private SpyMemcachedClient memcachedClient;
//	
//	private static final long COUNTER_LOG_HOME = 2L;
//
//	JsonMapper jsonMapper = JsonMapper.buildNormalMapper();
//
//	@Override
//	public void sendLogStore(String id, String queryStr, String clientStr) {
//		String url = String.format("%s/log/log!saveDownload.action?id=%s&queryString=%s&cs=%s",	Constants.UMS2_SHOW, id, queryStr, clientStr);
//		pool.execute(new StoreLogSender(url));
//	}
//
//	class StoreLogSender implements Runnable {
//
//		private String url;
//
//		public StoreLogSender(String url) {
//			super();
//			this.url = url;
//		}
//
//		@Override
//		public void run() {
//			connectUrl(url);
//		}
//
//		private String connectUrl(String url) {
//			try {
//				int status = -1;
//				String data = null;
//				int times = 0;
//				while (status != HttpStatus.SC_OK) {
//					HttpResponse response = HttpUtils.post(url, "utf-8");
//					try {
//						status = response.getStatusLine().getStatusCode();
//						HttpEntity entity = response.getEntity();
//						if (entity != null) {
//							data = EntityUtils.toString(entity, "utf-8");
//						}
//					} catch (Exception e1) {
//					}
//
//					if (status == HttpStatus.SC_OK) {
//						break;
//					}
//					times++;
//					if (times > 3) {
//						return null;
//					}
//					try {
//						Thread.sleep(5 * 1000);
//					} catch (Exception e) {
//					}
//				}
//				return data;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//
//	}
//
//	@Override
//	public void saveLog(String requestMethod, HttpServletRequest request)
//			throws Exception {
//		if (requestMethod.equals(METHOD_AD_XML)) {
//			return;
//		}
//		Map<String, Object> requestParam = request.getParameterMap();
//		StringBuilder buffer = new StringBuilder();
//		LogForStoreVo log = new LogForStoreVo();
//		log.setRequestMethod(requestMethod);
//		Set<Entry<String, Object>> keys = requestParam.entrySet();
//		for (Entry<String, Object> e : keys) {
//			String k = e.getKey();
//			String v = ((String[]) e.getValue())[0];
//			if (v != null && !v.isEmpty()) {
//				v = new String(v.getBytes("iso-8859-1"), "utf-8");
//			}
//			if (k.equals(PARA_IMEI)) {
//				log.setImei(v);
//			} else if (k.equals(PARA_IMSI)) {
//				log.setImsi(v);
//			} else if (k.equals(PARA_DOWNLOAD_METHOD)) {
//				log.setDownType(v);
//			} else if (k.equals(PARA_LANGUAGE)) {
//				log.setLanguage(v);
//			} else if (k.equals(PARA_RESOLUTION)) {
//				log.setResolution(v);
//			} else if (k.equals(PARA_STORE_TYPE)) {
//				log.setStoreType(v);
//			} else if (k.equals(PARA_CLIENT_VERSION)) {
//				log.setClientVersion(v);
//			} else if (k.equals(PARA_FROM_MARKET)) {
//				log.setFromMarket(v);
//			} else {
//				buffer.append(k).append(":").append(v).append(",");
//			}
//		}
//		String params = removeLastChara(buffer.toString());
//		log.setRequestParams(params);
//		log.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//		if (!isAvailable(log)) {
//			String userAgent = request.getHeader("User-Agent");
//			logger.warn("参数过长: " + jsonMapper.toJson(log) + ",User-Agent:"
//					+ userAgent);
//			return;
//		}
//		String appName = request.getParameter(PARA_APP_NAME);
//		if (appName == null) {
//			appName = "";
//		} else {
//			appName = new String(appName.getBytes("iso-8859-1"), "utf-8");
//		}
//		log.setAppName(appName);
//		saveByCache(BeanMapper.map(log, LogForStore.class));
//	}
//
//	private String selectTable() {
//		String tableSuffix = DateUtil.format(new Date(), "yyyyMM");
//		return tableSuffix;
//	}
//
//	private boolean isAvailable(LogForStoreVo log) {
//		if (StringUtils.length(log.getImei()) > 50) {
//			return false;
//		}
//		if (StringUtils.length(log.getImei()) > 50) {
//			return false;
//		}
//		if (StringUtils.length(log.getClientVersion()) > 20) {
//			return false;
//		}
//		if (StringUtils.length(log.getDownType()) > 10) {
//			return false;
//		}
//		if (StringUtils.length(log.getFromMarket()) > 255) {
//			return false;
//		}
//		if (StringUtils.length(log.getLanguage()) > 20) {
//			return false;
//		}
//		if (StringUtils.length(log.getRequestMethod()) > 255) {
//			return false;
//		}
//		if (StringUtils.length(log.getRequestParams()) > 255) {
//			return false;
//		}
//		if (StringUtils.length(log.getResolution()) > 20) {
//			return false;
//		}
//		if (StringUtils.length(log.getStoreType()) > 20) {
//			return false;
//		}
//		return true;
//	}
//
//	private String removeLastChara(String str) {
//		return StringUtils.substring(str, 0, str.length() - 1);
//	}
//
//	@Autowired
//	public void setLogForStoreDao(LogForStoreDao logForStoreDao) {
//		this.logForStoreDao = logForStoreDao;
//	}
//
//	@Override
//	public void saveAppDownload(HttpServletRequest request) {
//		String queryStr = request.getParameter("queryString");
//		if (StringUtils.isNotBlank(queryStr) && queryStr.contains("UMS")) {
//			queryStr = StringUtils.substringAfterLast(queryStr, "UMS/");
//		}
//		String clientStr = request.getParameter("cs");
//		LogForStore log = splitClientStr(clientStr);
//		int index = StringUtils.indexOf(queryStr, "&inputPath");
//		int ques = StringUtils.indexOf(queryStr, "?");
//		if (ques != -1) {
//			log.setRequestMethod(StringUtils.substring(queryStr, 0, ques));
//		} else {
//			log.setRequestMethod("d_download");
//		}
//		if (index != -1) {
//			queryStr = StringUtils.substring(queryStr, ques + 1, index);
//			String[] qs = StringUtils.split(queryStr, "=");
//			log.setRequestParams(qs[0] + ":" + qs[1]);
//		} else {
//			log.setRequestParams(queryStr);
//		}
//		log.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//		if (log.getAppName() == null) {
//			log.setAppName("");
//		}
//		saveByCache(log);
//	}
//
//	private LogForStore splitClientStr(String requetParam) {
//		LogForStore log = new LogForStore();
//		if (StringUtils.isNotBlank(requetParam)) {
//			String[] params = StringUtils.split(requetParam, "&");
//			for (int i = 0; i < params.length; i++) {
//				String param = params[i];
//				String[] keyValue = StringUtils.split(param, "=");
//				if (keyValue.length > 1) {
//					String key = keyValue[0];
//					String value = keyValue[1];
//					if (key.equals(Constants.PARA_CLIENT_VERSION)) {
//						log.setClientVersion(value);
//					}
//					if (key.equals(Constants.PARA_DOWNLOAD_METHOD)) {
//						log.setDownType(value);
//					}
//					if (key.equals(Constants.PARA_FROM_MARKET)) {
//						log.setFromMarket(value);
//					}
//					if (key.equals(Constants.PARA_IMEI)) {
//						log.setImei(value);
//					}
//					if (key.equals(Constants.PARA_IMSI)) {
//						log.setImsi(value);
//					}
//					if (key.equals(Constants.PARA_LANGUAGE)) {
//						log.setLanguage(value);
//					}
//					if (key.equals(Constants.PARA_RESOLUTION)) {
//						log.setResolution(value);
//					}
//					if (key.equals(Constants.PARA_STORE_TYPE)) {
//						log.setStoreType(value);
//					}
//				}
//			}
//		}
//		return log;
//	}
//	
//	@Override
//	public void saveByCache(LogForStore entity) {
//		String counter = MemcachedObjectType.COUNTER_PAGE.getPrefix();
//		int expTime = MemcachedObjectType.COUNTER_PAGE.getExpiredTime();
//		long count = cachedLogs(counter, expTime, MemcachedObjectType.LOG_PAGE.getPrefix(), JSONObject.toJSONString(entity));
//		if (count % COUNTER_LOG_HOME == 0) {
//			memcachedClient.set(counter, expTime, "0");
//			batchLogInHomes(count);
//		}
//	}
//	
//	private long cachedLogs(String counterKey, int exp, String logKey,
//			String logValue) {
//		long counter = memcachedClient.getMemcachedClient().incr(counterKey, 1);
//		if (counter == -1) {
//			memcachedClient.getMemcachedClient().add(counterKey, exp, "1");
//			counter = 1;
//		}
//		memcachedClient.set(logKey + counter, exp, logValue);
//		return counter;
//	}
//
//	private void batchLogInHomes(long contentSize) {
//		Map<String, Object> results = getFromCached(MemcachedObjectType.LOG_PAGE.getPrefix(), contentSize);
//		logForStoreDao.save(results);
//	}
//	
//	private Map<String, Object> getFromCached(String logKey, long count ) {
//		Map<String, Object> data = Maps.newHashMap();
//		String tableName = selectTable();
//		data.put("tableName", tableName);
//		List<Map<String, Object>> logs = Lists.newArrayList();
//		for (int i = 1; i <= count; i++) {
//			try {
//				Map<String, Object> log = JSONObject.parseObject(StringUtil.valueOf(memcachedClient.get(logKey + i)));
//				logs.add(log);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		data.put("logForStores", logs);
//		return data;
//	}
//
//	@Autowired
//	public void setMemcachedClient(SpyMemcachedClient memcachedClient) {
//		this.memcachedClient = memcachedClient;
//	}

}
