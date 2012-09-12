/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler;

import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * A base class for the down stream handler.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Sep 29, 2010
 */
public abstract class SimpleDownStreamHandler implements DownStreamHandler {

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelHandler#getName()
	 */
	@Override
	public abstract String getName();

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelHandler#handleDownstream(com.starcite.marketplace.channel.spi.HandlerContext, com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public void handleDownstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {
		if (e instanceof MessageEvent)
			handle(ctx, (MessageEvent)e);
		else {
			/*do nothing here now*/
		}
		ctx.sendDownstream(e);
	}

	
	
	/**
	 * Handle the process during the period.
	 * @param ctx
	 * @param e
	 */
	protected abstract void handle(HandlerContext ctx, MessageEvent e) throws HandlerException;

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelHandler#handleUpstream(com.starcite.marketplace.channel.spi.HandlerContext, com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public void handleUpstream(HandlerContext ctx, ChannelEvent e)
			throws HandlerException {
		throw new IllegalStateException("please do not use the upstream method during in the downstream handler.");

	}

}
