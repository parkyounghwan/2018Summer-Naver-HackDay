package com.shoppingmallparsing.batch.parser;

import java.util.HashMap;
import java.util.Map;

import com.shoppingmallparsing.batch.model.interpark.ShopItem;
import com.shoppingmallparsing.batch.model.lumid.LumidItem;

public class TSVShopItemParser implements ShoppingItemParser{
	private static final String TSV_DELIMITER = "\t";

	@Override
	public LumidItem lumidParse(String item, Map<String, Integer> headerMap) {
		LumidItem lumidItem = new LumidItem();
		Map<Integer, String> itemValue = new HashMap<>();

		String[] items = item.split(TSV_DELIMITER);
		for(int i=0; i<items.length; i++){
			itemValue.put(i, items[i]);
		}

		lumidItem.setId(itemValue.get(headerMap.get("id")));
		lumidItem.setTitle(itemValue.get(headerMap.get("title")));
		lumidItem.setPrice_pc(itemValue.get(headerMap.get("price_pc")));
		lumidItem.setLink(itemValue.get(headerMap.get("link")));
		lumidItem.setCategory_name1(itemValue.get(headerMap.get("category_name1")));
		lumidItem.setCategory_name2(itemValue.get(headerMap.get("category_name2")));
		lumidItem.setCategory_name3(itemValue.get(headerMap.get("category_name3")));
		lumidItem.setCategory_name4(itemValue.get(headerMap.get("category_name4")));
		lumidItem.setModel_number(itemValue.get(headerMap.get("model_number")));
		lumidItem.setBrand(itemValue.get(headerMap.get("brand")));
		lumidItem.setMaker(itemValue.get(headerMap.get("maker")));
		lumidItem.setOrigin(itemValue.get(headerMap.get("origin")));
		lumidItem.setPoint(itemValue.get(headerMap.get("point")));
		lumidItem.setShipping(itemValue.get(headerMap.get("shipping")));

		return lumidItem;
	}

	@Override
	public ShopItem interparkParse(String item, Map<String, Integer> headerMap) {
		ShopItem shopItem = new ShopItem();
		Map<Integer, String> itemValue = new HashMap<>();

		String[] items = item.split(TSV_DELIMITER);
		for(int i=0; i<items.length; i++){
			itemValue.put(i, items[i]);
		}

		shopItem.setId(itemValue.get(headerMap.get("id")));
		shopItem.setTitle(itemValue.get(headerMap.get("title")));
		shopItem.setPricePc(itemValue.get(headerMap.get("price_pc")));
		shopItem.setPriceMobile(itemValue.get(headerMap.get("price_mobile")));
		shopItem.setNormalPrice(itemValue.get(headerMap.get("normal_price")));
		shopItem.setLink(itemValue.get(headerMap.get("link")));
		shopItem.setMobileLink(itemValue.get(headerMap.get("mobile_link")));
		shopItem.setImageLink(itemValue.get(headerMap.get("image_link")));
		shopItem.setCategoryName1(itemValue.get(headerMap.get("category_name1")));
		shopItem.setCategoryName2(itemValue.get(headerMap.get("category_name2")));
		shopItem.setCategoryName3(itemValue.get(headerMap.get("category_name3")));
		shopItem.setCategoryName4(itemValue.get(headerMap.get("category_name4")));
		shopItem.setNaverCategory(itemValue.get(headerMap.get("naver_category")));
		shopItem.setNaverProductId(itemValue.get(headerMap.get("naver_product_id")));
		shopItem.setCondition(itemValue.get(headerMap.get("condition")));
		shopItem.setImportFlag(itemValue.get(headerMap.get("import_flag")));
		shopItem.setProductFlag(itemValue.get(headerMap.get("product_flag")));
		shopItem.setAdult(itemValue.get(headerMap.get("adult")));
		shopItem.setModelNumber(itemValue.get(headerMap.get("model_number")));
		shopItem.setBrand(itemValue.get(headerMap.get("brand")));
		shopItem.setMaker(itemValue.get(headerMap.get("maker")));
		shopItem.setOrigin(itemValue.get(headerMap.get("origin")));
		shopItem.setCardEvent(itemValue.get(headerMap.get("card_event")));
		shopItem.setEventWords(itemValue.get(headerMap.get("event_words")));
		shopItem.setCoupon(itemValue.get(headerMap.get("coupon")));
		shopItem.setInterestFreeEvent(itemValue.get(headerMap.get("interest_free_event")));
		shopItem.setPoint(itemValue.get(headerMap.get("point")));
		shopItem.setInstallationCosts(itemValue.get(headerMap.get("installation_costs")));
		shopItem.setSearchTag(itemValue.get(headerMap.get("search_tag")));
		shopItem.setReviewCount(itemValue.get(headerMap.get("review_count")));
		shopItem.setShipping(itemValue.get(headerMap.get("shipping")));
		shopItem.setDeliveryGrade(itemValue.get(headerMap.get("delivery_grade")));
		shopItem.setDeliveryDetail(itemValue.get(headerMap.get("delivery_detail")));
		shopItem.setSellerId(itemValue.get(headerMap.get("seller_id")));

		return shopItem;
	}
}
