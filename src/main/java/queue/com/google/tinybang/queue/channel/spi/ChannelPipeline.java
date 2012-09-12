/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

/**
 * A list of the handlers attached to the channel.
 * <p> {@link com.google.tinybang.queue.channel.handler.UpStreamHandler} and {@link com.google.tinybang.queue.channel.handler.DownStreamHandler} created for the data handling binding with {@link Channel}
 * <pre>
 * DATA------>----------------|
 *			\|/
 * 			{@link Channel}						{@link Channel}
 * 				|						|
 * 	----kill--	{@link com.google.tinybang.queue.channel.handler.DownStreamHandler}I					{@link com.google.tinybang.queue.channel.handler.UpStreamHandler} II
 * 	|			|						|
 * 	|---kill--	{@link com.google.tinybang.queue.channel.handler.DownStreamHandler}II				{@link com.google.tinybang.queue.channel.handler.UpStreamHandler} I
 *        \|/			|					   |
 * {@link com.google.tinybang.queue.channel.handler.ChannelLimboHandler}  	{@link com.google.tinybang.queue.channel.producer.IProducer}						{@link com.google.tinybang.queue.channel.consumer.IConsumer}
 * 				|					 |
 * 				|	---->	{@link java.util.Queue}	----->		 |
 * </pre>
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 15, 2010
 */
public abstract class ChannelPipeline {
	
	public abstract void addLimboHandler(ChannelHandler channelHandler);
	
	public abstract void addLast(String identifier, ChannelHandler channelHandler);
	
	public abstract void sendDownStream(ChannelEvent channelEvent);
	
	public abstract void sendUpStream(ChannelEvent channelEvent);
	
	public abstract void attach(Channel channel);

	public abstract Channel getChannel();
	
	public abstract void remove(String identifier);
}
