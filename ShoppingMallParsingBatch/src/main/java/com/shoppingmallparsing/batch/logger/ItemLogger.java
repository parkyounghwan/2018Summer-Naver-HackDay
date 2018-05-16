package com.shoppingmallparsing.batch.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemLogger {
	public static final int MODE_UPDATE = 100, MODE_INSERT = 200, MODE_DELETE = 300, MODE_DUPLICATE = 400;

	private File logFile;
	private Date now;
	private SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
	private BufferedWriter bufferedWriter;

	public ItemLogger(String filePath){
		this.now = new Date();
		this.logFile = new File(filePath+formatter.format(now)+".txt");
	}

	public void write(int mode, String log){
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));

			bufferedWriter.write(mode + log);
			bufferedWriter.flush();

			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
