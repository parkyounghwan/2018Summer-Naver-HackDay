package com.naver.park.feed.job.readstep;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.naver.park.feed.job.reader.EpFileReader;
import com.naver.park.feed.model.Product;
import com.naver.park.feed.util.EpDownloader;

public class EpFeedReader implements ItemReader<Product>, StepExecutionListener {

	private String eplocalPath = System.getProperty("user.home")+"/ep.txt";
	private String epUrl = "https://leocomfile.blob.core.windows.net/navershopping/navershopping.tsv";

	private EpDownloader epDownloader;
	private EpFileReader epFileReader;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		epDownloader = new EpDownloader(eplocalPath);
		epDownloader.epDownload(epUrl);

		String header = epDownloader.getHeader();
		System.out.format("header: %s \n", header);

		epFileReader = new EpFileReader(header, eplocalPath);
		epFileReader.beforeStep(stepExecution);

		ExecutionContext sc = stepExecution.getExecutionContext();
		sc.put("EP_HEADER", header);
	}


	@Override
	public Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (epFileReader.getReader() == null) {
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
		if (epFileReader != null) {
			epFileReader.getReader().close();
		}

		return ExitStatus.COMPLETED;
	}

}