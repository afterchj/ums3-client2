package com.service;

import java.util.List;

public interface SearchService {

	void save(String searchName);

	List<String> findByUser();

	List<String> findByOfficial();

	void refreshHottestSearchNames();

	void deleteExpiresLog();
}
