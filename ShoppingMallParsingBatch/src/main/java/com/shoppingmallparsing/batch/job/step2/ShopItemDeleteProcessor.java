package com.shoppingmallparsing.batch.job.step2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shoppingmallparsing.batch.job.execute.SuperStepExecution;
import com.shoppingmallparsing.batch.model.ShopItem;
import com.shoppingmallparsing.batch.parser.TSVShopItemParser;
import com.shoppingmallparsing.batch.util.TSVFileUtil;

@Component("shopItemDeleteProcessor")
@StepScope
public class ShopItemDeleteProcessor extends SuperStepExecution<String> implements ItemProcessor<List<ShopItem>, ShopItem>{
	private Set<String> latestItemIdSet = new HashSet<>();

	private BufferedReader bufferedReader;

	@Autowired
	private TSVFileUtil tsvFileUtil;

	private ShopItem shopItem;
	private String shopId;

	@Autowired
	private TSVShopItemParser tsvShopItemParser;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution){
		JobParameters context = stepExecution.getJobParameters();
		this.shopId = context.getString("shopId");

		//step1 데이터 받기
		super.setStepExecution(stepExecution);
		String latestFileName = (String) super.getData("LATEST_FILENAME");
		String line = null;

		File file = new File(this.tsvFileUtil.getFilePath(shopId) + latestFileName);
		try {
			this.bufferedReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while((line = this.bufferedReader.readLine()) != null ){
				shopItem = tsvShopItemParser.parse(line);
				shopItem.setShopId(shopId);
				latestItemIdSet.add(shopItem.getItemId());
			}
			this.bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ShopItem process(List<ShopItem> preItemList) throws Exception {
		for(int i=0; i<preItemList.size(); i++){
			String preItemId = preItemList.get(i).getItemId();
			if(!latestItemIdSet.contains(preItemId)){
				//삭제 할 데이터
				return preItemList.get(i);
			} else {
				continue;
			}
		}
		return null;
	}
}
