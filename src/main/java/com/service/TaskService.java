package com.service;

import java.util.List;
import java.util.Map;

import com.tpadsz.ctc.vo.Task;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.exception.TimeoutException;


public interface TaskService {

	void generateTasks();

	void init();

	List<Task> getTasks() throws SystemAlgorithmException, TimeoutException;

	Map<String, Map<String, List<Object>>> getUserTaskDataInfo(String uid) throws SystemAlgorithmException, TimeoutException;

	Integer getUserTaskInfo(String uid, String tid) throws TimeoutException;
	
	List<Task> getTpadTasks(String uid) throws SystemAlgorithmException, TimeoutException;

	List<String> getUserTaskInfo(String uid, List<String> tids) throws TimeoutException;
	
	List<Map> getDeepTasks(String uid) throws SystemAlgorithmException, TimeoutException;
	
}
