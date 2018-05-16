package com.shoppingmallparsing.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableBatchProcessing
@ImportResource({"classpath:launch-context.xml"})
public class ShoppingMallParsingBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingMallParsingBatchApplication.class, args);
	}
}
