/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.consumer.impl;

import com.google.tinybang.queue.channel.consumer.IConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 2, 2010
 */
public class BlockingQueueConsumer implements IConsumer {

	public BlockingQueue<Object> queue;
	
	public BlockingQueueConsumer(BlockingQueue<Object> queue) {
		this.queue = queue;
	}
	
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.consumer.IConsumer#receive()
	 */
	@Override
	public Object receive() {
		return this.queue.poll();
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.consumer.IConsumer#receive(long)
	 */
	@Override
	public Object receive(long timeout) {
		try {
			return this.queue.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.consumer.IConsumer#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.consumer.IConsumer#close()
	 */
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.consumer.IConsumer#stop()
	 */
	@Override
	public boolean reload(String queue) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.consumer.IConsumer#disable()
	 */
	@Override
	public boolean disable() {
		// TODO Auto-generated method stub
		return false;
	}

}
