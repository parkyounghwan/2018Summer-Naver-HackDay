package com.shoppingmallparsing.batch.job.step1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.shoppingmallparsing.batch.model.ShopItem;
import com.shoppingmallparsing.batch.util.TSVFileUtil;

@Component("shoppingItemWriter")
@StepScope
public class ShoppingItemWriter extends SuperStepExecution<String> implements ItemWriter<ShopItem>{

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
	public void write(List<? extends ShopItem> shopItem) throws Exception {
//		System.out.println("writer count: " + count++ + shopItem.size());

		List<ShopItem> sameList, modifiedList, newList;

		sameList = shopItem.parallelStream().filter(item -> !item.isModified() && !item.isDeleted() && !item.isUpdated()).collect(Collectors.toList());
		modifiedList = shopItem.parallelStream().filter(item -> item.isModified()).collect(Collectors.toList());
		newList = shopItem.parallelStream().filter(item -> item.isUpdated()).collect(Collectors.toList());

		if(sameList.size() != 0){
			itemLogger.write(ItemLogger.MODE_DUPLICATE, Arrays.toString(sameList.toArray()));
//			System.out.println("기존 데이터: " + sameList.size());
		}

		if(modifiedList.size() != 0){
			insertEntity = new HttpEntity<List<? extends ShopItem>>(modifiedList, headers);
			restTemplate.exchange(API_URL, HttpMethod.PUT, insertEntity, String.class);
			itemLogger.write(ItemLogger.MODE_UPDATE, Arrays.toString(modifiedList.toArray()));
//			System.out.println("수정 데이터: " + modifiedList.size());
		}

		if(newList.size() != 0){
			insertEntity = new HttpEntity<List<? extends ShopItem>>(newList, headers);
			restTemplate.exchange(API_URL, HttpMethod.POST, insertEntity, String.class);
			itemLogger.write(ItemLogger.MODE_UPDATE, Arrays.toString(newList.toArray()));
//			System.out.println("새로운 데이터: " + newList.size());
		}

		tsvFileUtil.writeList(fos, shopItem);
		super.putData("LATEST_FILENAME", tsvFileUtil.getFileName());
	}
}
