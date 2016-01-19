package com.service.impl;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;
import com.model.MonitorDealer;

public class ScheduledMonitorServiceTest extends ScheduledMonitorService {
	
	private ScheduledMonitorService monitorService = new ScheduledMonitorService();
	
	private MonitorDealer dealer = mock(MonitorDealer.class);
	
	private Map<String, Object> data = Maps.newHashMap();
	
	@Test
	public void testMonitorSuccess() throws Exception {
		when(dealer.start()).thenReturn(data);
		monitorService.monitor(dealer, 1000l, 1 * 1000l);
		verify(dealer, times(1)).success(data);
		verify(dealer, times(1)).stop();
		verify(dealer, times(1)).start();
	}
	
	@Test
	public void testMonitorFail() throws Exception {
		RuntimeException ex = new RuntimeException();
		when(dealer.start()).thenThrow(ex, ex, ex).thenReturn(data);
		monitorService.monitor(dealer, 1000l, 1 * 1000l);
		verify(dealer, times(4)).start();
		verify(dealer, times(4)).stop();
		verify(dealer, times(3)).fail(ex);
		verify(dealer, times(1)).success(data);
	}
	
	

}
