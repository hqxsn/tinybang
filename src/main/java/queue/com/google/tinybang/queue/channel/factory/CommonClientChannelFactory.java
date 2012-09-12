/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.factory;

import com.google.tinybang.queue.channel.ChannelFactory;
import com.google.tinybang.queue.channel.ClientChannel;
import com.google.tinybang.queue.channel.sink.ClientChannelSink;
import com.google.tinybang.queue.channel.spi.Channel;
import com.google.tinybang.queue.channel.spi.ChannelConfig;
import com.google.tinybang.queue.channel.spi.ChannelPipeline;
import com.google.tinybang.queue.channel.spi.ChannelSink;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 20, 2010
 */
public class CommonClientChannelFactory implements ChannelFactory {

	private ChannelSink channelSink;
	
	private ChannelConfig channelConfig;

	public CommonClientChannelFactory(ChannelConfig channelConfig) {
		channelSink = new ClientChannelSink(channelConfig);
		this.channelConfig = channelConfig;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.ChannelFactory#newChannel(com.starcite.marketplace.channel.spi.ChannelPipeline)
	 */
	@Override
	public Channel newChannel(ChannelPipeline pipeline) {
		Channel channel = new ClientChannel(pipeline, channelSink, channelConfig);
		return channel;
	}

}
