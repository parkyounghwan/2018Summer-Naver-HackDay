package com.naver.park.feed.job.readstep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.naver.park.feed.model.Product;
import com.naver.park.feed.util.TSVFileUtil;

public class EpFeedWriter implements StepExecutionListener, ItemWriter<Map<String, Product>> {

	private String apiUrl;

	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders httpHeaders = new HttpHeaders();
	private HttpEntity<List<? extends Product>> httpEntity;

	private ExecutionContext sc;

	@Autowired
	private TSVFileUtil tsvFileUtil;

	int count;

	private String epFileName;

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

		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		sc = stepExecution.getExecutionContext();
	}

	@Override
	public void write(List<? extends Map<String, Product>> items) throws Exception {
		System.out.format("readstep count: %d \n", ++count);

		List<Product> newList = new ArrayList<>();
		List<Product> updateList = new ArrayList<>();
		List<Product> allList = new ArrayList<>();

		for(Map<String, Product> item: items){
			if(item.containsKey("new")) {
				newList.add(item.get("new"));
				allList.add(item.get("new"));
			}
			if(item.containsKey("update")) {
				updateList.add(item.get("update"));
				allList.add(item.get("update"));
			}
			if(item.containsKey("all")) allList.add(item.get("all"));
		}

		updateList.remove(null);
		newList.remove(null);
		allList.remove(null);

		if(updateList.size() != 0) {
			System.out.format("updateList : %d \n", updateList.size());
			httpEntity = new HttpEntity<List<? extends Product>>(updateList, httpHeaders);
			restTemplate.exchange(apiUrl,  HttpMethod.PUT, httpEntity, Object.class);
		}

		if(allList.size() != 0) {
			System.out.format("allList : %d \n", allList.size());
		}

		if(newList.size() != 0) {
			System.out.format("newList : %d \n", newList.size());
			httpEntity = new HttpEntity<List<? extends Product>>(newList, httpHeaders);
			restTemplate.exchange(apiUrl,  HttpMethod.POST, httpEntity, Object.class);
		}

		preEpFile(allList);
		sc.put("LATEST_EP", tsvFileUtil.getFileName());
	}

	private void preEpFile(List<Product> allList) {

		String preEpDir = tsvFileUtil.getFilePath();
		String preEpFile = epFileName;

		if(preEpFile == null) {
			preEpFile = tsvFileUtil.getFileName();
		}

		String preFilepath = preEpDir + preEpFile;

		File epFile = new File(preFilepath);
		tsvFileUtil.createDirectoryIfNeed(epFile);

		try {
			FileOutputStream fos = new FileOutputStream(preFilepath, true);
			tsvFileUtil.writeList(fos, allList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return ExitStatus.COMPLETED;
	}
}
