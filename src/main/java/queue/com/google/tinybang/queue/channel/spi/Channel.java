/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

import com.google.tinybang.queue.channel.event.MessageEvent;

/**
 * consumer, or a producer.
 * <p> Supports to implement the channel as the JMS consumer and producer, also can extends NIO related framework (like: Netty, Mina, or purely NIO)
 * <p> The {@link com.google.tinybang.queue.channel.spi.Channel} defined for working with the Consumer and Producer, and the pipeline is binding with the {@link com.google.tinybang.queue.channel.spi.Channel}.
 * <pre>
 * 					   {@link com.google.tinybang.queue.channel.producer.IProducer} ------->----------------->--------------->---------
 * 						/|\					        \|/
 *   					{@link ChannelPipeline}						|	
 * 						/|\					        \|/	
 * 				-------------------------------------				|
 * 				        	|       {@link com.google.tinybang.queue.channel.spi.Channel}   |				        Queue
 * 				-------------------|------------------				\|/	
 * 						{@link com.google.tinybang.queue.channel.consumer.IConsumer}<------------------<----------------<----------
 * 						\|/
 * 						{@link ChannelPipeline}
 * </pre>
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 14, 2010
 */
public interface Channel {

	/**
	 * Supports write the data into the channel.
	 * @param object
	 * @return
	 */
	public ChannelFuture write(Object object) throws HandlerException;
	
	public ChannelPipeline getPipeline();
	
	public ChannelFuture connect();
	
	public ChannelSink getChannelSink();
	
	public ChannelConfig getChannelConfig();
	
	public MessageEvent newMessageEvent();
	
}
