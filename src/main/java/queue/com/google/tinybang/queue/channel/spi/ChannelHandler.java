/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

/**
 * Channel Handler supports to handle the events.
 * <p> the {@link com.google.tinybang.queue.channel.spi.ChannelHandler}s can be implemented as the policy, codec, and real handlers.
 * <p> All the data conversion, analysis and the handling are using the handler.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public interface ChannelHandler {

	public String getName();
    
	public void handleUpstream(HandlerContext ctx, ChannelEvent e) throws HandlerException ;
	
	public void handleDownstream(HandlerContext ctx, ChannelEvent e) throws HandlerException ;
	
}
