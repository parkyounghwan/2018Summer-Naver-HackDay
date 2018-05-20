package com.shoppingmallparsing.batch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class HTTPTextReader implements AutoCloseable{
	private BufferedReader bufferedReader;
	private String header;

	public HTTPTextReader(String url, String charSet){
		this(url, charSet, true);
	}

	public HTTPTextReader(String url, String charSet, boolean skipHead){
		InputStream inputStream = null;

		try {
			inputStream = new URL(url).openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(charSet)));

		if(skipHead){
			try {
				this.header = this.bufferedReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getHeader(){
		return this.header;
	}

	public String nextLine(){
		try {
			return this.bufferedReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void close() throws Exception {
		this.bufferedReader.close();
	}
}
