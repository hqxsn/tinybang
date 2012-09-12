/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler;

import com.google.tinybang.queue.channel.Channels;
import com.google.tinybang.queue.channel.Destination;
import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * Supports to communicate with another queue.
 * <p> the <tt>channelConfig.xml</tt> supports to config the communication, all the queue relationship must maintain in the <tt>channelConfig.xml</tt>.
 * <p> currently, if you want to submit the data to the queue or another queue, please simply config them accordingly.
 * <p> the outside (not queue) communicate with queue need not use this class to handle them. you can simply use <code>Channels.write(queue, message)</code> to write them.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 30, 2010
 */
public class MessageQueueCommunicationHandler implements UpStreamHandler, DownStreamHandler {
	
	private String name;
	
	private Destination queue;
	
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
	public void handleUpstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {
		
		if (e instanceof MessageEvent) {
			MessageEvent event = (MessageEvent) e;
			Channels.write(this.queue, event.getMessage());
		}
		
		ctx.next().sendUpstream(e);
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelHandler#handleDownstream(com.starcite.marketplace.channel.spi.HandlerContext, com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public void handleDownstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {
		
		if (e instanceof MessageEvent) {
			MessageEvent event = (MessageEvent) e;
			Channels.write(this.queue, event.getMessage());
		}
		
		ctx.next().sendDownstream(e);
	}

}
