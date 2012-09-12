/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.consumer;


/**
 * Supports to define the consumer factory.
 * the factory maintain the consumer, remove the dead consumer, and create new consumer.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 2, 2010
 */
public interface IConsumerFactory {

	public IConsumer getConsumer(String queue);
	
	public void close(IConsumer consumer);
	
}
