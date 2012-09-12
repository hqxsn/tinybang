/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.factory;

import com.google.tinybang.queue.channel.ChannelFactory;
import com.google.tinybang.queue.channel.ServerChannel;
import com.google.tinybang.queue.channel.sink.ServerChannelSink;
import com.google.tinybang.queue.channel.spi.Channel;
import com.google.tinybang.queue.channel.spi.ChannelConfig;
import com.google.tinybang.queue.channel.spi.ChannelPipeline;
import com.google.tinybang.queue.channel.spi.ChannelSink;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public class CommonServerChannelFactory implements ChannelFactory {
	
	private ChannelSink channelSink;
	
	private ChannelConfig channelConfig;

	public CommonServerChannelFactory(ChannelConfig channelConfig) {
		channelSink = new ServerChannelSink(channelConfig);
		this.channelConfig = channelConfig;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.ChannelFactory#newChannel(com.starcite.marketplace.channel.spi.ChannelPipeline)
	 */
	@Override
	public Channel newChannel(ChannelPipeline pipeline) {
		Channel channel = new ServerChannel(pipeline, channelSink, channelConfig);
		return channel;
	}

}
