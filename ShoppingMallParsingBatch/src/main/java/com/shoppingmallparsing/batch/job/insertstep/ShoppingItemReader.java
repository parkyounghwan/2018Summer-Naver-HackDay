package com.shoppingmallparsing.batch.job.insertstep;

import java.util.Map;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shoppingmallparsing.batch.job.execute.SuperStepExecution;
import com.shoppingmallparsing.batch.model.interpark.ShopItem;
import com.shoppingmallparsing.batch.parser.EPHeaderParser;
import com.shoppingmallparsing.batch.parser.TSVShopItemParser;
import com.shoppingmallparsing.batch.util.HTTPTextReader;

@Component("shoppingItemReader")
public class ShoppingItemReader extends SuperStepExecution<Map<String, Integer>> implements ItemReader<ShopItem>{

	private HTTPTextReader httpTextReader;
	private String dataRow;

	private Map<String, Integer> headerMap;

	private ShopItem shopItem;

	@Autowired
	private TSVShopItemParser tsvShopItemParser;

	@Autowired
	private EPHeaderParser epHeaderParser;

	private int count;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution){
		JobParameters context = stepExecution.getJobParameters();

		String epUrl = context.getString("url");
		String charSet = context.getString("charSet");
		String skipHeadYn = context.getString("skipHead");
		boolean skipHead = "Y".equals(skipHeadYn) ? true : false;

		this.httpTextReader = new HTTPTextReader(epUrl, charSet, skipHead);
		this.headerMap = epHeaderParser.headerKeySet(this.httpTextReader.getHeader());

		super.setStepExecution(stepExecution);
		super.putData("EP_HEADER", this.headerMap);
	}

	@Override
	public ShopItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		if((dataRow = httpTextReader.nextLine()) != null){
			shopItem = tsvShopItemParser.interparkParse(dataRow, headerMap);

			count++;
			if(count > 10000) return null;

			return shopItem;
		}else{
			this.httpTextReader.close();
			return null;
		}
	}
}