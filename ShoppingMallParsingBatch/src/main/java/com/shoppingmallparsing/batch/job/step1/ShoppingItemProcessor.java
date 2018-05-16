package com.shoppingmallparsing.batch.job.step1;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppingmallparsing.batch.model.ShopApiResponse;
import com.shoppingmallparsing.batch.model.ShopItem;

@Component("shoppingItemProcessor")
public class ShoppingItemProcessor implements ItemProcessor<ShopItem, ShopItem>{

	@Value("${mongodb.restapi.url}")
	private String apiUrl;

	private RestTemplate restTemplate;

	int count;
	@BeforeStep
	public void beforeStep(StepExecution stepExecution){
		this.restTemplate = new RestTemplate();
		this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}

	@Override
	public ShopItem process(ShopItem reqShopItem) throws Exception {
		System.out.println("process count: " + count++);
		StringBuilder apiGetUrl = new StringBuilder(apiUrl);
		apiGetUrl.append("?itemId=")
				.append(reqShopItem.getItemId())
				.append("&shopId=")
				.append(reqShopItem.getShopId());

		//reqShopItem(요청 데이터, 새로 들어오는 놈), resShopItem(응답 데이터, 저장되어 있던 놈)
		ShopApiResponse originalRow = restTemplate.getForObject(apiGetUrl.toString(), ShopApiResponse.class);

		if(originalRow.isSuccess() == true){
			ShopItem resShopItem = originalRow.getResult();
			if(reqShopItem.equals(resShopItem)){
				//동일한 데이터
				reqShopItem.setModified(false);
				reqShopItem.setDeleted(false);
				reqShopItem.setUpdated(false);
				return reqShopItem;
			} else {
				//수정된 데이터
				reqShopItem.setModified(true);
				reqShopItem.setDeleted(false);
				reqShopItem.setUpdated(false);
				return reqShopItem;
				}
		} else if(originalRow.isSuccess() == false && originalRow.getErrCode() == 404){
			//신상, EP에는 있고 DB에는 없는.
			reqShopItem.setModified(false);
			reqShopItem.setDeleted(false);
			reqShopItem.setUpdated(true);
			return reqShopItem;
		} else {
			return null;
		}
	}
}