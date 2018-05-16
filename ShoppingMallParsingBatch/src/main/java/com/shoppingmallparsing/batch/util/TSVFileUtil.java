package com.shoppingmallparsing.batch.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shoppingmallparsing.batch.model.ShopItem;

@Component("tsvFileUtil")
public class TSVFileUtil {

	private String TSV_FILE_DIRECTORY_PATH = "/Users/parkyounghwan/Documents/workspace-sts-3.8.4.RELEASE/ShoppingMallParsingBatch/csv/";

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
	private Date now = new Date();

	private String filePath;
	private String fileName;

	public String getFilePath(String shopId){
		String year = formatter.format(this.now).substring(0, 4);
		String month = formatter.format(this.now).substring(4, 6);

		this.filePath = TSV_FILE_DIRECTORY_PATH + shopId + "/" + year + "/" + month + "/";

		return this.filePath;
	}

	public String getFileName(){
		String dayMinute = formatter.format(this.now).substring(6, 12);
		this.fileName = dayMinute + ".txt";

		return this.fileName;
	}

	public String previousFileSearch(String filePath){
		File file = new File(filePath);
		String[] fileArr = file.list();
		if(fileArr.length == 0) {
			return null;
		} else {
			List<String> fileList = Arrays.asList(fileArr);
			Collections.reverse(fileList);
			return fileArr[1];
		}
	}

	public void writeList(FileOutputStream fos, List<? extends ShopItem> shopItem) {
		StringBuilder writeString = new StringBuilder();

		shopItem.forEach(item -> writeString.append(makeTSVRowString(item)));
		try {
			fos.write(writeString.toString().getBytes("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String makeTSVRowString(ShopItem shopItem){
		StringBuilder writeString = new StringBuilder();

		writeString.append(shopItem.getItemId())
		.append("\t")
		.append(shopItem.getTitle())
		.append("\t")
		.append(shopItem.getPricePc())
		.append("\t")
		.append(shopItem.getPriceMobile())
		.append("\t")
		.append(shopItem.getNormalPrice())
		.append("\t")
		.append(shopItem.getLink())
		.append("\t")
		.append(shopItem.getMobileLink())
		.append("\t")
		.append(shopItem.getImageLink())
		.append("\t")
		.append(shopItem.getCategoryName1())
		.append("\t")
		.append(shopItem.getCategoryName2())
		.append("\t")
		.append(shopItem.getCategoryName3())
		.append("\t")
		.append(shopItem.getCategoryName4())
		.append("\t")
		.append(shopItem.getNaverCategory())
		.append("\t")
		.append(shopItem.getNaverProductId())
		.append("\t")
		.append(shopItem.getCondition())
		.append("\t")
		.append(shopItem.getImportFlag())
		.append("\t")
		.append(shopItem.getProductFlag())
		.append("\t")
		.append(shopItem.getAdult())
		.append("\t")
		.append(shopItem.getModelNumber())
		.append("\t")
		.append(shopItem.getBrand())
		.append("\t")
		.append(shopItem.getMaker())
		.append("\t")
		.append(shopItem.getOrigin())
		.append("\t")
		.append(shopItem.getCardEvent())
		.append("\t")
		.append(shopItem.getEventWords())
		.append("\t")
		.append(shopItem.getCoupon())
		.append("\t")
		.append(shopItem.getInterestFreeEvent())
		.append("\t")
		.append(shopItem.getPoint())
		.append("\t")
		.append(shopItem.getInstallationCosts())
		.append("\t")
		.append(shopItem.getSearchTag())
		.append("\t")
		.append(shopItem.getReviewCount())
		.append("\t")
		.append(shopItem.getShipping())
		.append("\t")
		.append(shopItem.getDeliveryGrade())
		.append("\t")
		.append(shopItem.getDeliveryDetail())
		.append("\t")
		.append(shopItem.getSellerId())
		.append("\n");

		return writeString.toString();
	}
}
