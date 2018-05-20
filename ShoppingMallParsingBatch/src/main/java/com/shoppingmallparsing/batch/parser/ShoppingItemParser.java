package com.shoppingmallparsing.batch.parser;

import java.util.Map;

import com.shoppingmallparsing.batch.model.interpark.ShopItem;
import com.shoppingmallparsing.batch.model.lumid.LumidItem;

public interface ShoppingItemParser {
	public ShopItem interparkParse(String item, Map<String, Integer> headerMap);
	public LumidItem lumidParse(String item, Map<String, Integer> headerMap);
}
