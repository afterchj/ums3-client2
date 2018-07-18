package com.service;

import com.model.ThirdLogin;

/**
 * Created by nannan.li on 2018/7/11.
 */
public interface ThirdLoginSerive {

    com.tpadsz.uic.user.api.vo.AppUser getUserInfoById(ThirdLogin thirdLogin);

}
