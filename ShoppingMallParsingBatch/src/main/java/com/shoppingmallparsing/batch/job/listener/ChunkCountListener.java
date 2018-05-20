package com.shoppingmallparsing.batch.job.listener;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ChunkCountListener implements ChunkListener{

	private static final Logger LOG = LoggerFactory.getLogger(ChunkCountListener.class);

	private MessageFormat fmt = new MessageFormat("{0} items processed");

	private int loggingInterval = 1;

	@Override
	public void beforeChunk(ChunkContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterChunk(ChunkContext context) {
		int count = context.getStepContext().getStepExecution().getReadCount();

		if((count > 0) && (count % loggingInterval == 0)){
			LOG.info(fmt.format(new Object[] {new Integer(count)} ));
		}
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		// TODO Auto-generated method stub
	}

	public void setItemName(String itemName) {
		this.fmt = new MessageFormat("{0} " + itemName + " processed");
	}

	public void setLoggingInterval(int loggingInterval) {
		this.loggingInterval = loggingInterval;
	}
}
