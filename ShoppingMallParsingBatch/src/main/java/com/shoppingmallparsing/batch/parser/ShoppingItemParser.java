package com.shoppingmallparsing.batch.parser;

import com.shoppingmallparsing.batch.model.ShopItem;

public interface ShoppingItemParser {
	public ShopItem parse(String item);
}
