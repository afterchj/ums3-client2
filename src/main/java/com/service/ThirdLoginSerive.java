package com.service;

import com.model.DTpadUser;
import com.model.ThirdLogin;
import com.tpadsz.exception.ApplicationNotCorrectException;
import com.tpadsz.exception.MemcachedNotResponsedException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.exception.InvalidValidationCodeException;
import com.tpadsz.uic.user.api.exception.UserNotFoundException;
import com.web.vo.MobileVerifyType;

import java.util.Map;

/**
 * Created by nannan.li on 2018/7/11.
 */
public interface ThirdLoginSerive {

    com.tpadsz.uic.user.api.vo.AppUser getUserInfoById(ThirdLogin thirdLogin);

    void updateTpadUser(Map<String, Object> map);

    void updateAppUser(Map<String, Object> map);

    String getTpadIdByUid(String map);

    Map<String,Object> getAppUserByUid(String uid);

    Map<String,Object> findByMobile(String mobile);

    Map<String,Object> findByTpad(String id);

    DTpadUser getTpadUser(String uid) throws SystemAlgorithmException, UserNotFoundException,ApplicationNotCorrectException;


    boolean checkValidation(String code, String mobile, MobileVerifyType type) throws InvalidValidationCodeException, MemcachedNotResponsedException;


    void deleteValidation(String code, String mobile, MobileVerifyType register) throws MemcachedNotResponsedException;


    void setupMobile(DTpadUser tpadUser, String mobile) throws UserNotFoundException, ApplicationNotCorrectException, SystemAlgorithmException;
}
