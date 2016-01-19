package com.model;

import java.util.Map;

public interface MonitorDealer {
	
	public Map<String, Object> start() throws Exception;
	
	public void stop();
	
	public void success(Map<String, Object> data);
	
	public void fail(Exception ex);

}
