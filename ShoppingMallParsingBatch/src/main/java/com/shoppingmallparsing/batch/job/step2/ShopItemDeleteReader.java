package com.shoppingmallparsing.batch.job.step2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shoppingmallparsing.batch.job.execute.SuperStepExecution;
import com.shoppingmallparsing.batch.model.ShopItem;
import com.shoppingmallparsing.batch.parser.TSVShopItemParser;
import com.shoppingmallparsing.batch.util.TSVFileUtil;

@Component("shopItemDeleteReader")
@StepScope
public class ShopItemDeleteReader extends SuperStepExecution<String> implements ItemReader<List<ShopItem>> {
	private static final int PAGE_SIZE = 1000;

	@Value("${csv.previous.shopitem.path}")
	private String filePath;

	private File file;
	private BufferedReader  bufferedReader;

	@Autowired
	private TSVFileUtil tsvFileUtil;

	@Autowired
	private TSVShopItemParser tsvShopItemParser;

	private ShopItem shopItem;
	private String shopId;
	private String latestFileName;
	private String previousFileName;


	@BeforeStep
	public void beforeStep(StepExecution stepExecution){
		//shopId get
		JobParameters context = stepExecution.getJobParameters();
		String shopId = context.getString("shopId");

		//csv 파일 리스트 가져오기
		this.tsvFileUtil = new TSVFileUtil();
		String previoustFileName = this.tsvFileUtil.previousFileSearch(filePath);

		if(previoustFileName != null){
			//이전 파일 읽기
			this.file = new File(this.tsvFileUtil.getFilePath(shopId) + previoustFileName);
			try {
				this.bufferedReader = new BufferedReader(new FileReader(this.file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			this.previousFileName = null;
		};

		System.out.println("현재파일: " + this.latestFileName + " 이전파일: " + this.previousFileName);

	}

	@Override
	public List<ShopItem> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		//읽을 파일이 없을 경우
		if(this.previousFileName != null){
			List<ShopItem> list = new ArrayList<>();
			String previousItemRow = null;
			for(int i=0; i<PAGE_SIZE; i++) {
				if((previousItemRow = bufferedReader.readLine()) != null){
					shopItem = this.tsvShopItemParser.parse(previousItemRow);
					shopItem.setShopId(shopId);
					list.add(shopItem);

					continue;
				} else {
					return list;
				}
			}
			return null;
		} else {
			return null;
		}
	}
}
