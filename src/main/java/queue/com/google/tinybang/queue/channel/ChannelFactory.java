/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel;

import com.google.tinybang.queue.channel.spi.Channel;
import com.google.tinybang.queue.channel.spi.ChannelPipeline;

/**
 * Supports to create the {@link Channel} according the pipeline and the data.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public interface ChannelFactory {

	public Channel newChannel(ChannelPipeline pipeline);
	
}
