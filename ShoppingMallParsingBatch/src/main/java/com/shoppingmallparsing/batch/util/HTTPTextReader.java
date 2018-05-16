package com.shoppingmallparsing.batch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class HTTPTextReader implements AutoCloseable{
	private BufferedReader bufferedReader;

	public HTTPTextReader(String url, String charSet){
		this(url, charSet, true);
	}

	public HTTPTextReader(String url, String charSet, boolean skipHead){
		InputStream inputStream = null;

		try {
			//해당 쇼핑몰에 대한 ep문서(http url 형식)가져오기
			inputStream = new URL(url).openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//buffer에 읽어서 저장
		this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(charSet)));

		//header 제외
		if(skipHead){
			try {
				this.bufferedReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//한줄씩 읽어서 리턴
	public String nextLine(){
		try {
			return this.bufferedReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	//종료, try-with-exception 용, AutoCloseable 상속받아서 구현
	@Override
	public void close() throws Exception {
		this.bufferedReader.close();
	}
}
