/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler;

import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;

import java.text.MessageFormat;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 19, 2010
 */
public class JMSLoggingHandler extends SimpleUpStreamHandler {
    
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.jms.handler.SimpleUpStreamHandler#messageReceived(com.starcite.marketplace.jms.spi.HandlerContext, com.starcite.marketplace.jms.spi.ChannelEvent)
	 */
	@Override
	protected void messageReceived(HandlerContext ctx, ChannelEvent e) {

	}


}
