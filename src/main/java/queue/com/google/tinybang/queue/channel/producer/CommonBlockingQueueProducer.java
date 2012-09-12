/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.producer;

import java.util.concurrent.BlockingQueue;

import com.google.tinybang.queue.channel.event.MessageEvent;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 20, 2010
 */
public class CommonBlockingQueueProducer implements IProducer {

	private BlockingQueue<Object> queue;
	
	public CommonBlockingQueueProducer(BlockingQueue<Object> queue) {
		super();
		this.queue = queue;
	}



	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.producer.IProducer#produce(java.lang.Object)
	 */
	@Override
	public void produce(MessageEvent object) {
		try {
//			Message message = MessageBuilder.buildMessage(object.getMessage());
			this.queue.put(object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


    /* (non-Javadoc)
      * @see com.starcite.marketplace.channel.producer.IProducer#isValid()
      */
	@Override
	public boolean isValid() {
		return true;
	}



	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.producer.IProducer#reload()
	 */
	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return false;
	}



	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.producer.IProducer#disable()
	 */
	@Override
	public boolean disable() {
		// TODO Auto-generated method stub
		return false;
	}

}
