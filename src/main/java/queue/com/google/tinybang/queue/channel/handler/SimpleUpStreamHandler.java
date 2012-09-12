/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler;

import com.google.tinybang.queue.channel.Channels;
import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.event.ServerStateEvent;
import com.google.tinybang.queue.channel.spi.Channel;
import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * Based handler for the message received and connection.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 22, 2010
 */
public abstract class SimpleUpStreamHandler implements UpStreamHandler {
		
	protected String handlerName = null;

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.jms.spi.ChannelHandler#getName()
	 */
	@Override
	public String getName() {
		return handlerName;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.jms.spi.ChannelHandler#handleUpstream(com.starcite.marketplace.jms.spi.HandlerContext, com.starcite.marketplace.jms.spi.ChannelEvent)
	 */
	@Override
	public void handleUpstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {
		
		if (e instanceof MessageEvent) {
			MessageEvent me = (MessageEvent)e;
			messageReceived(ctx, me);
		}
		/*For the further usage*/
		else if (e instanceof ServerStateEvent) {
			ServerStateEvent event = (ServerStateEvent)e;
			if (event.getState() == ServerStateEvent.STATE.CONNECTED) {
				connect(event.getChannel());
			} else if (event.getState() == ServerStateEvent.STATE.RECONNETED) {
				reConnect(event.getChannel());
			}
				 
		}
		
		ctx.sendUpstream(e);
	}

	/**
	 * @param channel
	 */
	protected void reConnect(Channel channel) {
		
	}

	@Override
	public void handleDownstream(HandlerContext ctx, ChannelEvent e) {
		throw new IllegalStateException("Do not use this method to handle down stream related handler.");
	}

	/**
	 * @param ctx
	 * @param e
	 */
	protected abstract void messageReceived(HandlerContext ctx, ChannelEvent e) throws HandlerException;

	/**
	 * Will move the connection to the sub class, but currently, handle it here.
	 * @param channel
	 */
	protected void connect(Channel channel) {
		Channels.connect(channel);
	}

}
