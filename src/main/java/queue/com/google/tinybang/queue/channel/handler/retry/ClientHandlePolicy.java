/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler.retry;

import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.handler.SimpleDownStreamHandler;
import com.google.tinybang.queue.channel.spi.HandlerContext;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * An interface for the client handle retry policy.
 * <p>
 * If there is any exception happen during that period and after retry, will
 * throw out one retry failed exceptions.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a> Oct 22,
 *         2010
 */
public class ClientHandlePolicy extends SimpleDownStreamHandler {

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.handler.SimpleDownStreamHandler#getName()
	 */
	@Override
	public String getName() {
		return ClientHandlePolicy.class.getSimpleName();
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.handler.SimpleDownStreamHandler#handle(com.starcite.marketplace.channel.spi.HandlerContext, com.starcite.marketplace.channel.event.MessageEvent)
	 */
	@Override
	protected void handle(HandlerContext ctx, MessageEvent e) throws HandlerException {
		
	}

}
