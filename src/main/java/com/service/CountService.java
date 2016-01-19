package com.service;

public interface CountService {

	String countTotalDownload(String name);

	Long countWorkVisitors(String workId);

	Long countMemberVisitors(String memberId);

	Long countWorkPraisers(String workId);

	Long countUserVisitors(String uid);

}
