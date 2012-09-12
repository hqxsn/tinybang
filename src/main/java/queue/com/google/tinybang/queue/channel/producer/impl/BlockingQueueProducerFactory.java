/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.producer.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.google.tinybang.queue.channel.destination.BlockingQueueDestination;
import com.google.tinybang.queue.channel.producer.CommonBlockingQueueProducer;
import com.google.tinybang.queue.channel.producer.IProducer;
import com.google.tinybang.queue.channel.producer.IProducerFactory;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 13, 2010
 */
public class BlockingQueueProducerFactory implements IProducerFactory {
		
	private static Map<String, IProducer> _producers = new HashMap<String, IProducer>();
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.producer.IProducerFactory#getProducer(java.lang.String)
	 */
	@Override
	public IProducer getProducer(String queue) {
		BlockingQueue<Object> aBlockingQueue = BlockingQueueDestination.getInstance().getQueue(queue);
		if (!_producers.containsKey(queue)) {
			_producers.put(queue, new CommonBlockingQueueProducer(aBlockingQueue));
		}
		return _producers.get(queue);
	}
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.producer.IProducerFactory#returnProducer(java.lang.String, com.starcite.marketplace.channel.producer.IProducer)
	 */
	@Override
	public void returnProducer(String queue, IProducer producer) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.producer.IProducerFactory#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
