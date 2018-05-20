package com.shoppingmallparsing.batch.job.deletestep;

import java.util.List;

import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppingmallparsing.batch.model.interpark.ShopItem;

@Component("shopItemDeleteWriter")
public class ShopItemDeleteWriter implements ItemWriter<ShopItem>{

	@Value("${mongodb.restapi.url}")
	private String apiUrl;

	private RestTemplate restTemplate;
	private HttpHeaders headers;
	private	HttpEntity<List<? extends ShopItem>> entity;

	@BeforeStep
	public void beforStep(){
		this.restTemplate = new RestTemplate();
		this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		headers = new HttpHeaders();
		this.headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	public void write(List<? extends ShopItem> delShopItems) throws Exception {

		if(delShopItems.size() != 0){
			entity = new HttpEntity<List<? extends ShopItem>>(delShopItems, headers);
			restTemplate.exchange(apiUrl, HttpMethod.DELETE, entity, String.class);
			System.out.println("품절 데이터: " + delShopItems.size());
		}

	}
}
