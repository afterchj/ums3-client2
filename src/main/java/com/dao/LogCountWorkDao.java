package com.dao;

public interface LogCountWorkDao {

	Long getTotalWorkVisitors(String workId);

	Long getTotalWorkPraisers(String workId);

}
