package com.service;

import com.web.vo.ClientFileVo;

public interface ClientFileService {

	ClientFileVo getClientByVersion(String version);

	String getNewestVersionCode();

}
