/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.producer;

/**
 * Supports to provide the producer according the queue.
 * <p> Only one producer will be created for one queue, because the producer needs not take a long time. 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 12, 2010
 */
public interface IProducerFactory {

	public IProducer getProducer(String queue);
	
	/*the api is defined for the pool solution return.*/
	public void returnProducer(String queue, IProducer producer);
	
	public void stop();
	
}
