package com.naver.park.feed.job.deletestep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import com.naver.park.feed.mapper.FeedLineMapper;
import com.naver.park.feed.model.Product;
import com.naver.park.feed.util.TSVFileUtil;

public class EpDeleteProcessor implements ItemProcessor<Product, Product>, StepExecutionListener{

	private FlatFileItemReader<Product> fileReader;
	private File file;
	private BufferedReader br;

	private Set<String> latestItemId = new HashSet<>();

	@Autowired
	private TSVFileUtil tsvFileUtil;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobContext = jobExecution.getExecutionContext();
		String latestEp = (String) jobContext.get("LATEST_EP");
		String header = (String) jobContext.get("EP_HEADER");

		System.out.println("현재 파일: " + latestEp);
		file = new File(tsvFileUtil.getFilePath() + latestEp);
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fileReader = new FlatFileItemReader<Product>();
		fileReader.setResource(new FileSystemResource(tsvFileUtil.getFilePath() + latestEp));
		fileReader.setLineMapper(new FeedLineMapper<Product>() {{
	            setHeader(header.replace("class", "class_val"));
	            setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
	            setTargetType(Product.class);
	        }});
		}});
		fileReader.setEncoding("UTF-8");
		fileReader.open(stepExecution.getExecutionContext());

		try {
			while(fileReader != null){
				try {
					latestItemId.add(fileReader.read().getId());
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
		if(latestItemId.contains(item.getId())) {
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
