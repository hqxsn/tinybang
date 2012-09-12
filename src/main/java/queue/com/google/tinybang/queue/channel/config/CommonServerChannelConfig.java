/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.google.tinybang.queue.channel.spi.ChannelConfig;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonServerChannelConfig", propOrder = {
    "queue",
    "consumerCount",
    "workerCount",
    "mode",
    "isAync",
    "isBatch",
    "batchSize",
    "handler",
    "retryTimes",
    "undertaker"
    
})
public class CommonServerChannelConfig implements ChannelConfig {
	
	private static final int _default_retry_times = Integer.getInteger("jms.default.retry.times");
	
	private String queue;
	
	private int consumerCount;
	
	private int workerCount;
	
	private Mode mode;
	
	private List<ChannelConfig.Peer> handler;
	/*default set it as true, only customized for the special queue*/
	private boolean isAync = true;
	
	private boolean isBatch;
	
	private int batchSize;
	
	private int retryTimes = 3;
	/*default undertaker*/
	private String undertaker = "com.starcite.marketplace.channel.handler.undertaker.CommonFailedMessageUndertaker";
	
	/**
	 * @return the handler
	 */
	public List<Peer> getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(List<Peer> handler) {
		this.handler = handler;
	}

	/**
	 * @return the undertaker
	 */
	public String getUndertaker() {
		return undertaker;
	}

	/**
	 * @param undertaker the undertaker to set
	 */
	public void setUndertaker(String undertaker) {
		this.undertaker = undertaker;
	}

	public List<Peer> getHandlers() {
		return handler;
	}

	public void setHandlers(List<Peer> handlers) {
		this.handler = handlers;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public int getConsumerCount() {
		return consumerCount;
	}

	public void setConsumerCount(int consumerCount) {
		this.consumerCount = consumerCount;
	}

	public int getWorkerCount() {
		return workerCount;
	}

	public void setWorkerCount(int workerCount) {
		this.workerCount = workerCount;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelConfig#getMode()
	 */
	@Override
	public Mode getMode() {
		return this.mode;
	}

    /* (non-Javadoc)
      * @see com.starcite.marketplace.channel.spi.ChannelConfig#setMode(com.starcite.marketplace.channel.config.Mode)
      */
	@Override
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelConfig#isAync()
	 */
	@Override
	public boolean isAync() {
		return isAync;
	}

	/**
	 * @param isAync the isAync to set
	 */
	public void setAync(boolean isAync) {
		this.isAync = isAync;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelConfig#getBatchSize()
	 */
	@Override
	public int getBatchSize() {
		return this.batchSize;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelConfig#isBatch()
	 */
	@Override
	public boolean isBatch() {
		return this.isBatch;
	}

	/**
	 * @param isBatch the isBatch to set
	 */
	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}

	/**
	 * @param batchSize the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelConfig#getRetryTimes()
	 */
	@Override
	public int getRetryTimes() {
		return this.retryTimes;
	}
	
	
	public void setRetryTimes(int times) {
		this.retryTimes = times;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelConfig#getDefaultRetryTimes()
	 */
	@Override
	public int getDefaultRetryTimes() {
		return _default_retry_times;
	}
	
	
}
