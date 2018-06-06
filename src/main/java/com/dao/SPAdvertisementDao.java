package com.dao;

import com.model.SPAdvertisement;

import java.util.List;

public interface SPAdvertisementDao {
	
	public List<SPAdvertisement> getSpByType(String type);

}
