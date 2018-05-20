package com.shoppingmallparsing.batch.job.deletestep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shoppingmallparsing.batch.job.execute.SuperStepExecution;
import com.shoppingmallparsing.batch.model.interpark.ShopItem;
import com.shoppingmallparsing.batch.parser.TSVShopItemParser;
import com.shoppingmallparsing.batch.util.TSVFileUtil;

@Component("shopItemDeleteReader")
@StepScope
public class ShopItemDeleteReader extends SuperStepExecution<String> implements ItemReader<ShopItem> {
	private File file;
	private BufferedReader  bufferedReader;

	@Autowired
	private TSVFileUtil tsvFileUtil;

	@Autowired
	private TSVShopItemParser tsvShopItemParser;

	private String previousFileName;
	private Map<String, Integer> epHeader;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution){
		JobParameters context = stepExecution.getJobParameters();
		String shopId = context.getString("shopId");

		String filePath = this.tsvFileUtil.getFilePath(shopId);
		this.tsvFileUtil = new TSVFileUtil();
		this.previousFileName = this.tsvFileUtil.previousFileSearch(filePath);

		if(this.previousFileName != null){
			this.file = new File(this.tsvFileUtil.getFilePath(shopId) + this.previousFileName);
			try {
				this.bufferedReader = new BufferedReader(new FileReader(this.file));
				this.file.delete();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			this.previousFileName = null;
		};

		super.setStepExecution(stepExecution);
		this.epHeader = (Map<String, Integer>) super.getData("EP_HEADER");
	}

	@Override
	public ShopItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		//읽을 파일이 없을 경우
		if(this.previousFileName != null){
			ShopItem shopItem = null;
			String previousItemRow = null;
			if((previousItemRow = bufferedReader.readLine()) != null){
				shopItem = tsvShopItemParser.interparkParse(previousItemRow, epHeader);
				return shopItem;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
