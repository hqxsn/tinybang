/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler.retry;

import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.handler.SimpleDownStreamHandler;
import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * Supports to persist the failed data into the database for the further usage.
 * <p> you also can check the discard policy which is very simple, just discard this message without persistence you can check the {@link CommonDiscardRetryPolicy}.
 * <li> Solution 1: The data is persist via the event and the required queue as a wrapper class.
 * <li> Solution 2: Simply persist the event to the database, and persist the queue as the class name (or method name), just simply setup a rule for the persistence.
 * 
 * <p> Simply use the <code>Channels.manuallyRetry(queue, event);</code> to replay or retry again. 
 * <li> JMX supports to handle the manually retry.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Sep 29, 2010
 */
public class CommonLimboRetryPolicy extends SimpleDownStreamHandler {

	/**
	 * Provide the name of the policy (handle), please make sure it it unique.
	 */
	@Override
	public String getName() {
		return CommonLimboRetryPolicy.class.getSimpleName();
	}

	/*
	 * handle the retry accordingly.
	 */
	@Override
	public void handleDownstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {
//		int retryTime = e.getEventHeader().getRetryTimes();
//		int maxRetryTime = ctx.getChannel().getChannelConfig().getRetryTimes();
//		if (retryTime >= maxRetryTime) {
//			ctx.kill(e);
//		} else {
//			ctx.sendDownstream(e);
//		}
	}

	/* 
	 * Useless method will throw IllegalStateException. 
	 * @see com.starcite.marketplace.channel.handler.SimpleDownStreamHandler#handle(com.starcite.marketplace.channel.spi.HandlerContext, com.starcite.marketplace.channel.event.MessageEvent)
	 */
	@Override
	protected void handle(HandlerContext ctx, MessageEvent e)
			throws HandlerException {
		// do not use this handle method because already override the abstract method.
		throw new IllegalStateException("please do not use this method here, the handleDownstream method already been overriden!");
	}



}
