package com.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cache.MemcachedObjectType;
import com.google.common.collect.Sets;
import com.tpadsz.uic.user.api.vo.Firmware;
import com.utils.Constants;


public class UserServiceImplTest {
	
	private static UserServiceImpl userService = new UserServiceImpl();
	private static MemcachedClient client = mock(MemcachedClient.class);
	private static Firmware firmware;
	
	@BeforeClass
	public static void init(){
		userService.setMemcachedClient(client);
		firmware = new Firmware();
		firmware.setFm("fm");
		firmware.setClientVersion("cv");
		firmware.setImei("imei");
	}
	

	@Test
	public void testSeleteModeByFirmware1() throws TimeoutException, InterruptedException, MemcachedException {
		String key = String.format(MemcachedObjectType.CACHE_FILTERED_USER.getPrefix(), firmware.getFm(), firmware.getClientVersion());
		String numKey = String.format(MemcachedObjectType.CACHE_FILTERED_USER_NUM.getPrefix(), firmware.getFm(), firmware.getClientVersion());
		when(client.get(key)).thenReturn(null);
		when(client.get(numKey)).thenReturn(null);
		Integer mode = userService.selectModeByFirmware(firmware);
		assertThat(mode, equalTo(Constants.DISABLED));
		verify(client).set(numKey, MemcachedObjectType.CACHE_FILTERED_USER_NUM.getExpiredTime(), 1);
		verify(client).set(key, MemcachedObjectType.CACHE_FILTERED_USER.getExpiredTime(), Sets.newHashSet(firmware.getImei()));
	}
	
	@Test
	public void testSeleteModeByFirmware2() throws TimeoutException, InterruptedException, MemcachedException {
		String key = String.format(MemcachedObjectType.CACHE_FILTERED_USER.getPrefix(), firmware.getFm(), firmware.getClientVersion());
		String numKey = String.format(MemcachedObjectType.CACHE_FILTERED_USER_NUM.getPrefix(), firmware.getFm(), firmware.getClientVersion());
		when(client.get(key)).thenReturn(Sets.newHashSet(firmware.getImei()));
		when(client.get(numKey)).thenReturn(1);
		Integer mode = userService.selectModeByFirmware(firmware);
		assertThat(mode, equalTo(Constants.DISABLED));
	}

}
