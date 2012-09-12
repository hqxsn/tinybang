/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.destination;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 19, 2010
 */
public class BlockingQueueDestination implements Destination {

	private static ConcurrentMap<String, BlockingQueue<Object>> _queue_name_blocking_queue_mapping = new ConcurrentHashMap<String, BlockingQueue<Object>>();
	
	private BlockingQueueDestination () {};
	
	public static BlockingQueueDestination _blocking_queue_destination = new BlockingQueueDestination();
	
	public static BlockingQueueDestination getInstance() {
		return _blocking_queue_destination;
	}
	
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.destination.Destination#getQueue()
	 */
	@Override
	public BlockingQueue<Object> getQueue(String queueName) {
		if (_queue_name_blocking_queue_mapping.containsKey(queueName)) {
			// do nothing here.
		} else {
			_queue_name_blocking_queue_mapping.put(queueName, new LinkedBlockingQueue<Object>());
		}
		return  _queue_name_blocking_queue_mapping.get(queueName);
	}

}
