package com.shoppingmallparsing.batch.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shoppingmallparsing.batch.model.ShopInfo;

@Mapper
public interface ShopInfoMapper {

	@Select("SELECT id, url, shop_id as shopId, shop_name as shopName FROM shop WHERE shop_id = #{shopId}")
	public ShopInfo getAllShopInfo(@Param("shopId") String shopId);
}
