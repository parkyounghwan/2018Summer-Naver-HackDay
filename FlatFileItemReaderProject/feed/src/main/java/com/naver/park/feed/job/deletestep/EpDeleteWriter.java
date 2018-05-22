package com.naver.park.feed.job.deletestep;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.naver.park.feed.job.readstep.EpFeedWriter;
import com.naver.park.feed.model.Product;

public class EpDeleteWriter implements ItemWriter<Product>, StepExecutionListener{

	private String apiUrl;

	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders httpHeaders = new HttpHeaders();
	private HttpEntity<List<? extends Product>> httpEntity;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		Properties prop = new Properties();
		try {
			prop.load(EpFeedWriter.class.getClassLoader().getResourceAsStream("feed.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		apiUrl = prop.getProperty("mongo.api.url");
	}


	@Override
	public void write(List<? extends Product> items) throws Exception {
		if(items.size() != 0){
			httpEntity = new HttpEntity<List<? extends Product>>(items, httpHeaders);
			restTemplate.exchange(apiUrl, HttpMethod.DELETE, httpEntity, String.class);
			System.out.println("품절 데이터: " + items.size());
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
