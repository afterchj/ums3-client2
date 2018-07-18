package com.service.impl;

import com.dao.SearchDao;
import com.service.SearchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("searchService")
public class SearchServiceImpl implements SearchService {
	@Resource(name = "searchDao")
	private SearchDao searchDao;


	@Override
	public void save(String searchName) {
		searchDao.save(searchName);

	}

	@Override
	public List<String> findByUser() {
		List<String> hottestNames = searchDao.findByUser();
		return hottestNames;
	}

	@Override
	public List<String> findByOfficial() {
		List<String> officialNames = searchDao.findByOfficial();
		return officialNames;
	}

	@Override
	public void refreshHottestSearchNames() {
		try {
			searchDao.delete();
			searchDao.refreshHottestSearchNamesByUser();
			searchDao.refreshHottestSearchNamesByOfficial();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteExpiresLog() {
		searchDao.deleteExpiresLog();
	}


}
