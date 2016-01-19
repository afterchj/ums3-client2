package com.dao;

import com.model.ClientFile;

public interface ClientFileDao {

	ClientFile getClientByVersion(String version);

	String getNewestVersionCode();

}
