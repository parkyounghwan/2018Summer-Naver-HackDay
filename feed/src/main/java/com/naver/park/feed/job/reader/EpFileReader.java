package com.naver.park.feed.job.reader;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.FileSystemResource;

import com.naver.park.feed.mapper.FeedLineMapper;
import com.naver.park.feed.model.Product;

public class EpFileReader implements StepExecutionListener {

	private FlatFileItemReader<Product> fileReader;
	private String header;	//epDownload 에서 받아오기.
	private String eplocalPath;

	public EpFileReader(String header, String eplocalPath) {
		this.header = header;
		this.eplocalPath = eplocalPath;
	}

	@SuppressWarnings("rawtypes")
	public FlatFileItemReader getReader() {
		return this.fileReader;
	}

	//받아와야 할 값 : header
	//변하는 값 : Product, header > 'class'
//	public void fileOpen(String epLocalPath) {
//		fileReader = new FlatFileItemReader<Product>();
//
//		fileReader.setResource(new FileSystemResource(epLocalPath));
//		fileReader.setLineMapper(new FeedLineMapper<Product>() {{
//			setHeader(header.replace("class", "class_val"));
//			setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
//				setTargetType(Product.class);
//			}});
//		}});
//
//		fileReader.setEncoding("UTF-8");
//		fileReader.open(stepExecution.getExecutionContext());
//	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		fileReader = new FlatFileItemReader<Product>();

		fileReader.setResource(new FileSystemResource(eplocalPath));
		fileReader.setLineMapper(new FeedLineMapper<Product>() {{
			setHeader(header.replace("class", "class_val"));
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
				setTargetType(Product.class);
			}});
		}});

		fileReader.setEncoding("UTF-8");
		fileReader.open(stepExecution.getExecutionContext());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}


}
