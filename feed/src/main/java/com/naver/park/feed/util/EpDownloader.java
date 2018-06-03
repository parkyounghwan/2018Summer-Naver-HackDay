package com.naver.park.feed.util;

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

public class EpDownloader {

	private BufferedReader reader = null;
	private Writer writer = null;
	private FileOutputStream fos = null;
	private OutputStreamWriter osw = null;

	private String eplocalPath;
	public String header;

	public EpDownloader(String eplocalPath) {
		this.eplocalPath = eplocalPath;
	}

	public String getHeader() {
		return this.header;
	}

	public CloseableHttpClient createHttpClient() {
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

	public void epDownload(String epUrl) {

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
	          	  if (row > 50) {
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

	public static void createDirectoryIfNeed(File targetFile) {
		File parent = new File(targetFile.getParent());

		if (!parent.exists()) {
			parent.mkdirs();
		}
	}
}
