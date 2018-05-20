package com.shoppingmallparsing.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoppingmallparsing.batch.mapper.ShopInfoMapper;
import com.shoppingmallparsing.batch.model.ShopInfo;

@Controller
public class JobLauncherController {

	private ShopInfo shopInfo;

	@Autowired
	private ShopInfoMapper shopInfoMapper;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("job1")
	private Job job;

	@RequestMapping(path="/launcher")
	public String setShoppingMallUrl(){
		return "launcher";
	}

	@RequestMapping(path="/launched")
	@ResponseBody
	public String launched(@RequestParam("shopId") String shopId, @RequestParam("charSet") String charSet){

		JobExecution jobExecution;

		shopInfo = shopInfoMapper.getAllShopInfo(shopId);

		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.addString("shopId", shopId)
					.addString("shopName", shopInfo.getShopName())
					.addString("url", shopInfo.getUrl())
					.addString("charSet", charSet)
					.addString("skipHead", "Y")
					.toJobParameters();

			jobExecution = jobLauncher.run(job, jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "complete";
	}
}
