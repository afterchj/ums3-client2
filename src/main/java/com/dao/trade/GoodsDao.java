package com.dao.trade;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.trade.DGoods;

public interface GoodsDao {

	List<DGoods> getByShelf(@Param("sid")String sid, @Param("startIndex")int pageNo, @Param("pageSize")int pageSize);

	DGoods getCompleteGoodsById(String gid);

	DGoods getSimpleGoodsById(String gid);

	void updateStockAndSales(DGoods goods);

}
