package com.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.model.member.Work;
import com.tpadsz.exception.DisabledException;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.exception.ParamBlankException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.web.vo.WorkNewUploadVo;
import com.web.vo.WorkUploadVo;
import com.web.vo.WorkVo;

public interface WorkService {

	List<WorkVo> getAllByMemberWithPage(Long pageNo);

	List<WorkVo> getByMemberWithPage(String memberId, Long page);

	Long searchWorkVisitors(String memberId);

	WorkUploadVo generateUpload(String params);

	String save(WorkUploadVo uploadVo, MultipartFile file);

	List<WorkVo> getByMemberWithPage(String loginName, String type, Long pageNo);

	Long searchWorkPraisers(String workId);

	void searchWorkPraisers(List<WorkVo> workVos);

	void completeWorkVo(List<WorkVo> workVos);

	List<WorkVo> getAllByUserWithPage(Long pageNo);

	List<WorkVo> getByUserWithPage(String uid, Long pageNo);

	WorkNewUploadVo generateNewUpload(JSONObject params);

	String save(WorkNewUploadVo uploadVo, MultipartFile file) throws DisabledException, SystemAlgorithmException, ParamBlankException;

	List<Work> findByMemberWithPageWithoutUid(String memberId, Long pageNo);

	void updateUid(String id, String uid) throws NotExecutedDbException;
	
}
