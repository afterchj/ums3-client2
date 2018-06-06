package com.service.impl;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.model.MonitorDealer;
import com.service.MonitorService;
import com.utils.LoggerUtils;

@Service("monitorService")
public class ScheduledMonitorService implements MonitorService {

	private Logger logger = LoggerUtils.SYSTEM;
	private Logger daylog = LoggerUtils.DAYLOG;

	private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);

	@Override
	public void monitor(MonitorDealer dealer, Long initialDelay, Long period) {
		MonitorThread command = new MonitorThread(dealer);
		ScheduledFuture<String> result = threadPool.schedule(command, initialDelay,
				TimeUnit.MILLISECONDS);
		while (true) {
			try {
				daylog.warn("result is get!");
				String code = result.get();
				dealer.stop();
				if (StringUtils.equalsIgnoreCase("success", code)) {
					result.cancel(true);
					return;
				} else {
					 result = threadPool.schedule(command, period, TimeUnit.MILLISECONDS);
				}
			} catch (Exception e) {
				logger.error("bean:monitorService, method:monitor", e);
			}
		}
	}

	class MonitorThread implements Callable<String> {

		private MonitorDealer dealer;

		public MonitorThread(MonitorDealer dealer) {
			super();
			this.dealer = dealer;
		}

		@Override
		public String call() throws Exception {
			try {
				daylog.warn("MonitorThread is called~");
				Map<String, Object> data = dealer.start();
				dealer.success(data);
				return "success";
			} catch (Exception e) {
				dealer.fail(e);
			}
			return "fail";
		}

	}

}
