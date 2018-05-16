package com.shoppingmallparsing.batch.job.configue;

import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingItemBatchConfiguration {

	@Bean(name="promotionListener")
	public ExecutionContextPromotionListener promotionListener() {
		ExecutionContextPromotionListener executionContextPromotionListener =
				new ExecutionContextPromotionListener();

		executionContextPromotionListener.setKeys(new String[]{"LATEST_FILENAME"});

		return executionContextPromotionListener;
	}
}
