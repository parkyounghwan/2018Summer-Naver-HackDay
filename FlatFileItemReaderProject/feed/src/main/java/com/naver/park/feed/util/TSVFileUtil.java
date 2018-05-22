package com.naver.park.feed.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.naver.park.feed.model.Product;

public class TSVFileUtil {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	private Date now = new Date();

	private String filePath;
	private String fileName;

	public void createDirectoryIfNeed(File targetFile) {
		File parent = new File(targetFile.getParent());

		if (!parent.exists()) {
			parent.mkdirs();
		}
	}

	public String getFilePath(){
		String year = formatter.format(this.now).substring(0, 4);
		String month = formatter.format(this.now).substring(4, 6);

		filePath = System.getProperty("user.home") + "/pre_ep" + "/" + year + "/" + month + "/";

		return filePath;
	}

	public String getFileName(){
		String dayMinute = formatter.format(this.now).substring(6, 14);
		fileName = dayMinute + ".txt";

		return fileName;
	}

	public String previousFileSearch(String filePath){
		File file = new File(filePath);

		String[] fileArr = file.list();

		if(fileArr.length <= 1) {
			return null;
		} else {
			List<String> fileList = Arrays.asList(fileArr);
			Collections.reverse(fileList);
			return fileArr[1];
		}
	}

	public void writeList(FileOutputStream fos, List<Product> items) {
		StringBuilder writeString = new StringBuilder();

		for(Product item: items){
			writeString.append(makeTSVRowString(item));
		}

		try {
			fos.write(writeString.toString().getBytes("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String makeTSVRowString(Product product){
		StringBuilder writeString = new StringBuilder();

		writeString.append(product.getId())
		.append("\t")
		.append(product.getTitle())
		.append("\t")
		.append(product.getPrice_pc())
		.append("\t")
		.append(product.getLink())
		.append("\t")
		.append(product.getImage_link())
		.append("\t")
		.append(product.getCategory_name1())
		.append("\t")
		.append(product.getNaver_category())
		.append("\t")
		.append(product.getCondition())
		.append("\t")
		.append(product.getManufacture_define_number())
		.append("\t")
		.append(product.getMaker())
		.append("\t")
		.append(product.getMinimum_purchase_quantity())
		.append("\t")
		.append(product.getShipping())
		.append("\t")
		.append(product.getEvent_words())
		.append("\t")
		.append(product.getClass_val())
		.append("\t")
		.append(product.getUpdate_time())
		.append("\n");

		return writeString.toString();
	}
}

