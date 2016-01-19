package com.service;

import java.util.List;

import com.web.vo.CoinVo;

public interface CoinService {

	List<CoinVo> findCoinMap(String uid);

	List<CoinVo> generateNewCoins();

}
