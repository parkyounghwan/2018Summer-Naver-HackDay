package com.shoppingmallparsing.batch.job.insertstep;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppingmallparsing.batch.model.ShopApiResponse;
import com.shoppingmallparsing.batch.model.interpark.ShopItem;

@Component("shoppingItemProcessor")
public class ShoppingItemProcessor implements ItemProcessor<ShopItem, Map<String, ShopItem>>{

	@Value("${mongodb.restapi.url}")
	private String API_URL;

	private RestTemplate restTemplate;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution){
		this.restTemplate = new RestTemplate();
		this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}

	@Override
	public Map<String, ShopItem> process(ShopItem reqShopItem) throws Exception {
		StringBuilder apiGetUrl = new StringBuilder(API_URL);
		apiGetUrl.append("?id=")
				.append(reqShopItem.getId());

		ShopApiResponse originalRow = restTemplate.getForObject(apiGetUrl.toString(), ShopApiResponse.class);

		Map<String, ShopItem> allMap = new HashMap<>();
		Map<String, ShopItem> updateMap = new HashMap<>();
		Map<String, ShopItem> newMap = new HashMap<>();

		//신상
		if(originalRow.isSuccess() == false && originalRow.getErrCode() == 404) {
			newMap.put("new", reqShopItem);
			return newMap;
		}
		//수정
		else if(!originalRow.getResult().equals(reqShopItem)) {
			updateMap.put("update", reqShopItem);
			return updateMap;
		}
		else {
			allMap.put("all", reqShopItem);
			return allMap;
		}

	}
}