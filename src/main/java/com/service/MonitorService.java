package com.service;

import com.model.MonitorDealer;

public interface MonitorService {

	void monitor(MonitorDealer dealer, Long initialDelay, Long period);

}
