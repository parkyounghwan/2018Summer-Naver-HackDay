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
import org.springframework.beans.factory.annotation.Autowired;

import com.naver.park.feed.job.reader.EpFileReader;
import com.naver.park.feed.model.Product;
import com.naver.park.feed.util.TSVFileUtil;

public class EpDeleteReader implements ItemReader<Product>, StepExecutionListener{

	private EpFileReader epFileReader;
	private File file;

	@Autowired
	private TSVFileUtil tsvFileUtil;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		String preEpName = tsvFileUtil.previousFileSearch(tsvFileUtil.getFilePath());

		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobContext = jobExecution.getExecutionContext();

		String header = (String) jobContext.get("EP_HEADER");

		System.out.println("이전 파일: " + preEpName);

		if(preEpName != null) {
			file = new File(tsvFileUtil.getFilePath() + preEpName);
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			epFileReader = new EpFileReader(header, tsvFileUtil.getFilePath() + preEpName);
			epFileReader.beforeStep(stepExecution);
		}
	}

	@Override
	public Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (epFileReader.getReader() == null || epFileReader.getReader() == null) {
			return null;
		}
		try {
			return (Product) epFileReader.getReader().read();
		} catch (Exception e) {
			return (Product) epFileReader.getReader().read();
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (epFileReader.getReader() != null) {
			epFileReader.getReader().close();
			file.delete();
		}
		return ExitStatus.COMPLETED;
	}

}
