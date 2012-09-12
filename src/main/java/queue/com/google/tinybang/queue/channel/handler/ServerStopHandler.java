/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler;

import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 5, 2010
 */
public class ServerStopHandler implements DownStreamHandler {

	private String name;
	
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelHandler#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelHandler#handleUpstream(com.starcite.marketplace.channel.spi.HandlerContext, com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public void handleUpstream(HandlerContext ctx, ChannelEvent e) {
		throw new IllegalArgumentException("server stop handler is an down stream handler, please do not use the up stream handler");
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelHandler#handleDownstream(com.starcite.marketplace.channel.spi.HandlerContext, com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public void handleDownstream(HandlerContext ctx, ChannelEvent channelEvent) {
		ctx.getChannel().getChannelSink().stop();
	}



}
