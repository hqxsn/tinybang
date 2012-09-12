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
 * Retry policy as a handler to handle the retry related tasks.
 * <p>
 * {@link com.google.tinybang.queue.channel.handler.retry.CommonDiscardRetryPolicy} supports to discard the message after the
 * configured retry times.
 * <p>
 * Please be careful to use this policy because the message cannot be found in
 * the MQ, BQ (aka: BlockingQueue), and the database.
 * <p>
 * The policy is defined in the <code>ChannelConfig</code>, and you also can add
 * it as {@link com.google.tinybang.queue.channel.spi.ChannelHandler} in the Bootstrap classes.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a> Sep 29,
 *         2010
 */
public class CommonDiscardRetryPolicy extends SimpleDownStreamHandler {

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.handler.SimpleDownStreamHandler#getName()
	 */
	@Override
	public String getName() {
		return CommonDiscardRetryPolicy.class.getName();
	}
	
	/** 
	 * Supports to handle retry and do nothing if the retry time more than configured time.
	 */
	@Override
	public void handleDownstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {
//		int retryTime = e.getEventHeader().getRetryTimes();
//		int maxRetryTime = ctx.getChannel().getChannelConfig().getRetryTimes();
//		if (retryTime >= maxRetryTime) {
//			// do not invoke next down stream handler because it is out of retry times.
//		} else {
//			ctx.sendDownstream(e);
//		}
	}



	/**
	 *  Need not handle anything during the handle, because the handleDownstream already been override.
	 * 
	 */
	@Override
	protected void handle(HandlerContext ctx, MessageEvent e) throws HandlerException {
		// do nothing here.
	}

}
