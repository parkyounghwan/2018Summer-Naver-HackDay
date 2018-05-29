package com.naver.park.feed.job.readstep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.FileSystemResource;

import com.naver.park.feed.mapper.FeedLineMapper;
import com.naver.park.feed.model.Product;

public class EpFeedReader implements ItemReader<Product>, StepExecutionListener {

	private FlatFileItemReader<Product> fileReader;
	private String eplocalPath = System.getProperty("user.home")+"/ep.txt";
	private String header;

	@Override
	public void beforeStep(StepExecution stepExecution) {

		epDownload("~");
		System.out.println();
		System.out.println("header" + header);

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

		ExecutionContext sc = stepExecution.getExecutionContext();
		sc.put("EP_HEADER", header);
	}


	@Override
	public Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (fileReader == null) {
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
		}

		return ExitStatus.COMPLETED;
	}

	private void epDownload(String epUrl) {
		BufferedReader reader = null;
		Writer writer = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		CloseableHttpClient httpClient = createHttpClient();

		try {
	       HttpGet httpGet = new HttpGet(epUrl);

	        httpGet.setConfig(RequestConfig.custom()
	        		.setConnectionRequestTimeout(60000)
	        		.setSocketTimeout(60000)
	        		.setConnectTimeout(60000)
	        		.setContentCompressionEnabled(false)
	        		.build());

	        long startTime = System.currentTimeMillis();
	        System.out.println(System.getProperty("user.home"));
	        File epFile = new File(eplocalPath);
	        createDirectoryIfNeed(epFile);

	        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

	        for(org.apache.http.Header header  : httpResponse.getAllHeaders()) {
	        	System.out.println(header.getName() + " - " + header.getValue());
	        }

	        System.out.println("::GET Response Status::" + httpResponse.getStatusLine().getStatusCode());

	        int row = 0;
	        if (httpResponse.getStatusLine().getStatusCode() == 200) {

	        	reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
	        	fos = new FileOutputStream(epFile);
			    osw = new OutputStreamWriter(fos, "UTF-8");
			    writer = new BufferedWriter(osw);
			    header = reader.readLine();

	        	String line ="";
	            row = 0;

	            while((line = reader.readLine()) != null) {
	          	   row++;

	          	  writer.write(line + System.lineSeparator());
	          	  //System.out.println(line);
	          	  if (row > 2000) {
	          		  row--;
	          		  break;
	          	  }

	            }

	            writer.flush();
	        }


	       int elaspsedTime = (Math.round((System.currentTimeMillis() - startTime) / 1000));
	       int hour, min, sec;

	       sec = elaspsedTime % 60;
	       min = elaspsedTime / 60 % 60;
	       hour = elaspsedTime / 3600;

	       System.out.println(String.format("%02d:%02d:%02d", hour, min, sec));
	       System.out.println("건수 : " + row);


	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		if (reader != null)
	    			reader.close();
	    			httpClient.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }

	}

	private static CloseableHttpClient createHttpClient()  {
		return HttpClients.custom()
			.addInterceptorLast(new HttpRequestInterceptor() {

            @Override
			public void process(
                    final HttpRequest request,
                    final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                	request.addHeader("Accept-Encoding", "gzip");
                }
            }

        })
        .addInterceptorLast(new HttpResponseInterceptor() {

            @Override
			public void process(
                    final HttpResponse response,
                    final HttpContext context) throws HttpException, IOException {
                HttpEntity entity = response.getEntity();
                org.apache.http.Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    org.apache.http.HeaderElement[] codecs = ceheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(
                                    new GzipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }
            }

        }).build();
	}

	public static void createDirectoryIfNeed(File targetFile) {
		File parent = new File(targetFile.getParent());

		if (!parent.exists()) {
			parent.mkdirs();
		}
	}

}
