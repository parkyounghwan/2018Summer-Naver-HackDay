package com.shoppingmallparsing.batch.job.insertstep;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppingmallparsing.batch.job.execute.SuperStepExecution;
import com.shoppingmallparsing.batch.logger.ItemLogger;
import com.shoppingmallparsing.batch.model.interpark.ShopItem;
import com.shoppingmallparsing.batch.util.TSVFileUtil;

@Component("shoppingItemWriter")
@StepScope
public class ShoppingItemWriter extends SuperStepExecution<String> implements ItemWriter<Map<String, ShopItem>>{

	@Value("${log.file.path}")
	private String LOG_PATH;

	@Value("${mongodb.restapi.url}")
	private String API_URL;

	private FileOutputStream fos;

	private RestTemplate restTemplate;
	private HttpHeaders headers;
	private HttpEntity<List<? extends ShopItem>> insertEntity;

	private ItemLogger itemLogger;

	@Autowired
	private TSVFileUtil tsvFileUtil;
	int count;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution){
		JobParameters context = stepExecution.getJobParameters();
		String shopId = context.getString("shopId");

		try {
			this.fos = new FileOutputStream(this.tsvFileUtil.getFilePath(shopId) +
					this.tsvFileUtil.getFileName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//api call
		this.restTemplate = new RestTemplate();
		this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		headers = new HttpHeaders();
		this.headers.setContentType(MediaType.APPLICATION_JSON);

		//log
		this.itemLogger = new ItemLogger(LOG_PATH);

		//data convey to step2
		super.setStepExecution(stepExecution);
	}

	@Override
	public void write(List<? extends Map<String, ShopItem>> shopItem) throws Exception {

		List<ShopItem> allList = new ArrayList<>();
		List<ShopItem> newList = new ArrayList<>();
		List<ShopItem> updateList = new ArrayList<>();

		for(Map<String, ShopItem> item: shopItem){
			if(item.containsKey("new")) newList.add(item.get("new"));
			if(item.containsKey("update")) updateList.add(item.get("update"));
			if(item.containsKey("all")) allList.add(item.get("all"));
		}

		if(newList.size() != 0) {
			System.out.println("new count : " + newList.size());

			insertEntity = new HttpEntity<List<? extends ShopItem>>(newList, headers);
			restTemplate.exchange(API_URL, HttpMethod.POST, insertEntity, Object.class);
		}

		if(updateList.size() != 0) {
			System.out.println("update count : " + updateList.size());

			insertEntity = new HttpEntity<List<? extends ShopItem>>(updateList, headers);
			restTemplate.exchange(API_URL, HttpMethod.PUT, insertEntity, Object.class);
		}

		if(allList.size() != 0){
			System.out.println("all count : " + allList.size());
			
			tsvFileUtil.writeList(fos, allList);
			super.putData("LATEST_FILENAME", tsvFileUtil.getFileName());
		}
	}
}