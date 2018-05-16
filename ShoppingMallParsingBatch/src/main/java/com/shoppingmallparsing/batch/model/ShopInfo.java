package com.shoppingmallparsing.batch.model;

public class ShopInfo {

	int id;
	String url;
	String shopId;		//shop_id;
	String shopName;	//shop_name;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName(){
		return shopName;
	}
	public void setShopNaame(String shopName){
		this.shopName = shopName;
	}
}
