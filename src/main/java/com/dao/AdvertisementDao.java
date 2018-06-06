package com.dao;

import java.util.List;

import com.model.Advertisement;

public interface AdvertisementDao {
	
	public List<Advertisement> getByType(String type);

	public List<Advertisement> getClientAds();
	
}
