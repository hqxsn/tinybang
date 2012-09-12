/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.consumer.impl;

import com.google.tinybang.queue.channel.consumer.IConsumer;
import com.google.tinybang.queue.channel.consumer.IConsumerFactory;
import com.google.tinybang.queue.channel.destination.BlockingQueueDestination;

/**
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 19, 2010
 */
public class BlockingQueueConsumerFactory implements IConsumerFactory {


	private static BlockingQueueDestination destination = BlockingQueueDestination.getInstance();
	
	
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.consumer.IConsumerFactory#close(com.starcite.marketplace.channel.consumer.IConsumer)
	 */
	@Override
	public void close(IConsumer consumer) {
		consumer.close();
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.consumer.IConsumerFactory#getConsumer(com.starcite.marketplace.channel.Destination)
	 */
	@Override
	public IConsumer getConsumer(String queue) {
		IConsumer consumer = new BlockingQueueConsumer(destination.getQueue(queue));
		return consumer;
	}
	
	

}
