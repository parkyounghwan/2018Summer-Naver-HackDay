package com.shoppingmallparsing.batch.parser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("epHeaderParser")
public class EPHeaderParser {

	private String seperator = "\t";

	public Map<String, Integer> headerKeySet(String header) {
		Map<String, Integer> headerMap = new HashMap<>();

		String[] keys = header.split(seperator);

		for(int i=0; i<keys.length; i++){
			headerMap.put(keys[i], i);
		}

		return headerMap;
	}
}
