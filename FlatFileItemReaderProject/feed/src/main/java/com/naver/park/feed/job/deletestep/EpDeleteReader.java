package com.naver.park.feed.job.deletestep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import com.naver.park.feed.mapper.FeedLineMapper;
import com.naver.park.feed.model.Product;
import com.naver.park.feed.util.TSVFileUtil;

public class EpDeleteReader implements ItemReader<Product>, StepExecutionListener{

	private FlatFileItemReader<Product> fileReader;
	private File file;
	private BufferedReader br;

	private String preEpName;

	@Autowired
	private TSVFileUtil tsvFileUtil;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		preEpName = tsvFileUtil.previousFileSearch(tsvFileUtil.getFilePath());

		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobContext = jobExecution.getExecutionContext();
		String header = (String) jobContext.get("EP_HEADER");

		if(preEpName.equals(".DS_Store")) preEpName = null;
		System.out.println("이전 파일: " + preEpName);
		if(preEpName != null) {
			file = new File(tsvFileUtil.getFilePath() + preEpName);
			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			fileReader = new FlatFileItemReader<Product>();
			fileReader.setResource(new FileSystemResource(tsvFileUtil.getFilePath() + preEpName));
			fileReader.setLineMapper(new FeedLineMapper<Product>() {{
		            setHeader(header.replace("class", "class_val"));
		            setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
		            setTargetType(Product.class);
		        }});
			}});
			fileReader.setEncoding("UTF-8");
			fileReader.open(stepExecution.getExecutionContext());
		}
	}

	@Override
	public Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (fileReader == null || preEpName == null) {
			return null;
		}
		try {
			return fileReader.read();
		} catch (Exception e) {
			return fileReader.read();
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (fileReader != null) {
			fileReader.close();
			file.delete();
		}
		return ExitStatus.COMPLETED;
	}

}
