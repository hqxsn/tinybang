/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;


/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public interface ChannelSink {
	
	public ChannelFuture eventSunk(ChannelPipeline pipeline);
	
	public ChannelFuture eventSunk(ChannelPipeline pipeline, ChannelEvent event);
	
	public boolean stop();

}
