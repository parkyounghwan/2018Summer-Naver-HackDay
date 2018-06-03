package com.naver.park.feed.job.deletestep;

import java.util.HashSet;
import java.util.Set;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.naver.park.feed.job.reader.EpFileReader;
import com.naver.park.feed.model.Product;
import com.naver.park.feed.util.TSVFileUtil;

public class EpDeleteProcessor implements ItemProcessor<Product, Product>, StepExecutionListener{

	private Set<String> latestItemId = new HashSet<>();
	private EpFileReader epFileReader;

	@Autowired
	private TSVFileUtil tsvFileUtil;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobContext = jobExecution.getExecutionContext();

		String latestEp = (String) jobContext.get("LATEST_EP");
		String header = (String) jobContext.get("EP_HEADER");

		System.out.println("현재 파일: " + latestEp);

		epFileReader = new EpFileReader(header, tsvFileUtil.getFilePath() + latestEp);
		epFileReader.beforeStep(stepExecution);

		try {
			Product product;
			while((product = (Product) epFileReader.getReader().read()) != null){
				try {
					latestItemId.add(product.getId());
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			System.out.format("현재 파일 item count: %d \n", latestItemId.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Product process(Product item) throws Exception {
		if(item == null) {
			return null;
		}
		if(!latestItemId.contains(item.getId())) {
			return item;
		} else {
			return null;
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
