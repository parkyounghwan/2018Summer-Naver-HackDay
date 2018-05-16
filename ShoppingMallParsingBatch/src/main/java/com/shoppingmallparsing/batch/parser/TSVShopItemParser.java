package com.shoppingmallparsing.batch.parser;

import com.shoppingmallparsing.batch.model.ShopItem;

public class TSVShopItemParser implements ShoppingItemParser{
	private static final String TSV_DELIMITER = "\t";

	@Override
	public ShopItem parse(String item) {
		ShopItem shopItem = new ShopItem();

		String[] items = item.split(TSV_DELIMITER);

		shopItem.setItemId(items[0]);
		shopItem.setTitle(items[1]);
		shopItem.setPricePc(items[2]);
		shopItem.setPriceMobile(items[3]);
		shopItem.setNormalPrice(items[4]);
		shopItem.setLink(items[5]);
		shopItem.setMobileLink(items[6]);
		shopItem.setImageLink(items[7]);
		shopItem.setCategoryName1(items[8]);
		shopItem.setCategoryName2(items[9]);
		shopItem.setCategoryName3(items[10]);
		shopItem.setCategoryName4(items[11]);
		shopItem.setNaverCategory(items[12]);
		shopItem.setNaverProductId(items[13]);
		shopItem.setCondition(items[14]);
		shopItem.setImportFlag(items[15]);
		shopItem.setProductFlag(items[16]);
		shopItem.setAdult(items[17]);
		shopItem.setModelNumber(items[18]);
		shopItem.setBrand(items[19]);
		shopItem.setMaker(items[20]);
		shopItem.setOrigin(items[21]);
		shopItem.setCardEvent(items[22]);
		shopItem.setEventWords(items[23]);
		shopItem.setCoupon(items[24]);
		shopItem.setInterestFreeEvent(items[25]);
		shopItem.setPoint(items[26]);
		shopItem.setInstallationCosts(items[27]);
		shopItem.setSearchTag(items[28]);
		shopItem.setReviewCount(items[29]);
		shopItem.setShipping(items[30]);
		shopItem.setDeliveryGrade(items[31]);
		shopItem.setDeliveryDetail(items[32]);
		shopItem.setSellerId(items[33]);

		return shopItem;
	}
}
