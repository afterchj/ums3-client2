package com.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.ClientFileDao;
import com.mapper.BeanMapper;
import com.model.ClientFile;
import com.service.ClientFileService;
import com.utils.Constants;
import com.web.vo.ClientFileVo;

@Service("clientFileService")
public class ClientFileServiceImpl implements ClientFileService{
	private ClientFileDao clientFileDao;

	@Autowired
	public void setClientFileDao(ClientFileDao clientFileDao) {
		this.clientFileDao = clientFileDao;
	}

	@Override
	@Cacheable(value="clientFileService",key="'getClientByVersion' + #version")
	public ClientFileVo getClientByVersion(String version) {
		ClientFile clientFile = clientFileDao.getClientByVersion(version);
		return convert(clientFile);
	}
	
	
	private ClientFileVo convert(ClientFile clientFile) {
		ClientFileVo vo = BeanMapper.map(clientFile, ClientFileVo.class);
		vo.setPath(String.format("%s/files/%s", Constants.STATIC_SHOW,
				clientFile.getPath()));
		return vo;
	}

	private String getLockerClient(String version) throws Exception {

		if (StringUtils.isBlank(version)) {
			version = getNewestVersionCode();
		}
		ClientFileVo newClient = getClientByVersion(version);
		String url = null;
		if (newClient != null) {
			url = String.format("%s/files/%s", Constants.STATIC_SHOW,
					newClient.getPath());
		}
		return url;
	}

	@Override
	@Cacheable(value="clientFileService",key="'getNewestVersionCode'")
	public String getNewestVersionCode() {
		return clientFileDao.getNewestVersionCode();
	}
}
