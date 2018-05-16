package com.shoppingmallparsing.batch.job.step1;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shoppingmallparsing.batch.model.ShopItem;
import com.shoppingmallparsing.batch.parser.TSVShopItemParser;
import com.shoppingmallparsing.batch.util.HTTPTextReader;

@Component("shoppingItemReader")
public class ShoppingItemReader implements ItemReader<ShopItem>{

	private HTTPTextReader httpTextReader;
	private String dataRow;

	private ShopItem shopItem;

	private String shopId;
	private String shopName;

	@Autowired
	private TSVShopItemParser tsvShopItemParser;

	//갯수 제한
	private int count;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution){
		JobParameters context = stepExecution.getJobParameters();

		this.shopId = context.getString("shopId");
		this.shopName = context.getString("shopName");

		String epUrl = context.getString("url");
		String charSet = context.getString("charSet");
		String skipHeadYn = context.getString("skipHead");
		boolean skipHead = "Y".equals(skipHeadYn) ? true : false;

		this.httpTextReader = new HTTPTextReader(epUrl, charSet, skipHead);
	}

	@Override
	public ShopItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		if((dataRow = httpTextReader.nextLine()) != null){
			shopItem = tsvShopItemParser.parse(dataRow);
			shopItem.setShopId(shopId);
			shopItem.setShopName(shopName);
			shopItem.setModified(false);
			shopItem.setDeleted(false);
			shopItem.setUpdated(false);

			//갯수 제한
			if(count > 10) return null;

			System.out.println("reader count: " + count++);

			return shopItem;
		}else{
			this.httpTextReader.close();
			return null;
		}
	}
}
