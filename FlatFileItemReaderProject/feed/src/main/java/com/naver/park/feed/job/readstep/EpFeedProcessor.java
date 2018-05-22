package com.naver.park.feed.job.readstep;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.web.client.RestTemplate;

import com.naver.park.feed.model.APIResponse;
import com.naver.park.feed.model.Product;

public class EpFeedProcessor implements ItemProcessor<Product, Map<String, Product>>, StepExecutionListener{

	private RestTemplate restTemplate = new RestTemplate();
	private String apiUrl;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		Properties prop = new Properties();
		try {
			prop.load(EpFeedProcessor.class.getClassLoader().getResourceAsStream("feed.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		apiUrl = prop.getProperty("mongo.api.url");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Product> process(Product item) throws Exception {
		StringBuilder getUrl = new StringBuilder(apiUrl);
		getUrl.append("?id=")
				.append(item.getId());

		APIResponse originalRow = restTemplate.getForObject(getUrl.toString(), APIResponse.class);

		Map<String, Product> updateMap = new HashMap();
		Map<String, Product> allMap = new HashMap();
		Map<String, Product> newMap = new HashMap();

		//신상
		if(originalRow.isSuccess() == false && originalRow.getErrCode() == 404) {
			newMap.put("new", item);
			return newMap;
		}
		//수정
		else if(!originalRow.getResult().equals(item)) {
			updateMap.put("update", item);
			return updateMap;
		}
		else {
			allMap.put("all", item);
			return allMap;
		}
	}
}
