/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel;

import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.spi.*;

/**
 * An client channel supports to handle client level request.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public class ClientChannel implements Channel {

	private ChannelPipeline channelPipeline;
	
	private ChannelSink channelSink;
	
	private ChannelConfig channelConfig;
	
	

	/**
	 * @param pipeline
	 * @param channelSink
	 * @param channelConfig
	 */
	public ClientChannel(ChannelPipeline pipeline, ChannelSink channelSink,
			ChannelConfig channelConfig) {
		this.channelConfig = channelConfig;
		this.channelSink = channelSink;
		this.channelPipeline = pipeline;
		this.channelPipeline.attach(this);
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.Channel#write(java.lang.Object)
	 */
	@Override
	public ChannelFuture write(Object object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.Channel#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() {
		return this.channelPipeline;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.Channel#connect(com.starcite.marketplace.channel.spi.ChannelConfig)
	 */
	@Override
	public ChannelFuture connect() {
		return Channels.connect(this);
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.Channel#getChannelSink()
	 */
	@Override
	public ChannelSink getChannelSink() {
		return this.channelSink;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.Channel#getChannelConfig()
	 */
	@Override
	public ChannelConfig getChannelConfig() {
		return this.channelConfig;
	}


	/*
	 * Just supports to create a clean message event, possibly, need compose some default data here.
	 */
	@Override
	public MessageEvent newMessageEvent() {
     return new MessageEvent();
	}


}
