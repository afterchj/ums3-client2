package com.service;

import com.tpadsz.uic.user.api.vo.Firmware;

public interface UserService {

	Long searchUserVisitors(String person);

	Integer selectModeByFirmware(Firmware firmware);

}
