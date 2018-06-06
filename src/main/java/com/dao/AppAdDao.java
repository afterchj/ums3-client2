package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.AppAd;

public interface AppAdDao {

	List<AppAd> getByDtype(@Param("dtype")String dtype, @Param("startIndex")Long startIndex, @Param("endIndex")Long endIndex);

}
