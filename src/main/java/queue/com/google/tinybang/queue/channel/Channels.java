/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel;

import com.google.tinybang.CommonLogger;
import com.google.tinybang.queue.channel.event.EventHeader;
import com.google.tinybang.queue.channel.event.Message;
import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.event.ServerStateEvent;
import com.google.tinybang.queue.channel.spi.Channel;
import com.google.tinybang.queue.channel.spi.ChannelFuture;
import com.google.tinybang.queue.channel.spi.ExceptionListener;
import com.google.tinybang.queue.channel.spi.HandlerException;
import com.sun.deploy.net.MessageHeader;

import java.text.MessageFormat;

/**
 * A helper class for the {@link Channel} to handle the operations.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public class Channels {
	
	
	private static CommonLogger _console_logger = new CommonLogger(Channels.class.getSimpleName());
	
	public static void fireMessageReceived(Channel channel, Object message) throws HandlerException {
		MessageEvent event = channel.newMessageEvent();
		event.setMessage(message);
		channel.getPipeline().sendUpStream(event);
	}

	/**
	 * @param channel
	 */
	public static ChannelFuture connect(Channel channel) {
		return channel.getChannelSink().eventSunk(channel.getPipeline());
	}
	
	public static void fireStop(Channel channel) throws HandlerException{
		channel.getPipeline().sendDownStream(new ServerStateEvent(channel, ServerStateEvent.STATE.CLOSE));
	}

	/**
	 * Write the object into the queue via producer.
	 * <p>
	 * Take care of the exception, this method is working without exception
	 * handling, the handler will check the exception, but if the handlers are
	 * working without the exceptionListeners, just support retry (the behavior depending which retry policy are set).
	 * */
	public static void write(Destination queue, Object object) {
		String queueName = queue.getDestinationName();
		fireMessageSent(ClientBootstrap.getInstance().getChannel(queueName), object);
	}
	/**
	 * You can specify how to handle the exception handling as the following steps
	 * <li> Find which handler has issue - context.getHandler().getName();
	 * <li> Find out the issue - event.getException();
	 * <p> How to resolve the issue 
	 * <li>persist it <code>context.kill(event.getEvent());</code>
	 * <li>handle the retry <code>Channels.retry(context.getChannel(), (MessageEvent)event.getEvent());</code>
	 *				
	 * */
	public static void write(Destination queue, Object object, ExceptionListener exceptionListener) {
		fireMessageSentWithExceptionListener(queue, object, exceptionListener);
	}
	
	private static void fireMessageSentWithExceptionListener(Destination queue, Object object, ExceptionListener exceptionListener) {
		Channel channel = ClientBootstrap.getInstance().getChannel(queue.getDestinationName());
		MessageEvent<Message> event = channel.newMessageEvent();
        Message message = new Message();
        message.setHeader(new EventHeader());
        message.setObject(object);
        event.setMessage(message);
		channel.getPipeline().sendDownStream(event);
	}

	public static void write(String queueName, Object object) throws HandlerException {
		fireMessageSent(ClientBootstrap.getInstance().getChannel(queueName), object);
	}
	
	public static void fireMessageSent(Channel channel, Object message) {
		MessageEvent event = channel.newMessageEvent();
		event.setMessage(message);

		channel.getPipeline().sendDownStream(event);
	}
	
	public static void retry(Channel channel, MessageEvent messageEvent) {
        _console_logger.warn(MessageFormat.format("Retry message {0} for the queue {1}", messageEvent, channel.getChannelConfig().getQueue()));
		String queue = channel.getChannelConfig().getQueue();
		retry(queue, messageEvent);
	}
	
	public static void retry(String queue, MessageEvent messageEvent) {
		Channel clientChannel = ClientBootstrap.getInstance().getChannel(queue);
		clientChannel.getPipeline().sendDownStream(messageEvent);
	}
	
	public static void manuallyRetry(String queue, MessageEvent messageEvent) {
		retry(queue, messageEvent);
	}
	
}
