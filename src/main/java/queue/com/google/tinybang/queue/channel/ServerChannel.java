/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel;

import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.spi.*;
/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public class ServerChannel implements Channel {
	
	private ChannelPipeline pipeline;
	
	private ChannelSink channelSink;

	private ChannelConfig channelConfig;
	
	/**
	 * @param pipeline
	 */
	public ServerChannel(ChannelPipeline pipeline, ChannelSink channelSink, ChannelConfig channelConfig) {
		this.pipeline = pipeline;
		this.channelSink = channelSink;
		this.pipeline.attach(this);
		this.channelConfig = channelConfig;
	}

     /* Currently, only return null as default, will add the future listener. 
      * (non-Javadoc)
      * @see com.starcite.marketplace.channel.spi.Channel#connect(com.starcite.marketplace.channel.spi.ChannelConfig)
      */
	@Override
	public ChannelFuture connect() {
		//Put the code to the config. the pipeline
		Channels.connect(this);
		//Currently, only return as default, the outside do not check it now.
		return null;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.Channel#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() {
		return this.pipeline;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.jms.spi.Channel#getChannelConfig()
	 */
	@Override
	public ChannelConfig getChannelConfig() {
		return this.channelConfig;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.jms.spi.Channel#getChannelSink()
	 */
	@Override
	public ChannelSink getChannelSink() {
		return this.channelSink;
	}
	
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.Channel#newMessageEvent()
	 */
	@Override
	public MessageEvent newMessageEvent() {
		return new MessageEvent();
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.Channel#write(java.lang.Object)
	 */
	@Override
	public ChannelFuture write(Object object) throws HandlerException {
        Channels.write(this.channelConfig.getQueue(), object);
        // return null as default, the channel future is for the futher usage.
		return null;
	}

}
