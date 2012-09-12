/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.destination;

import java.util.Queue;

/**
 * Supports the wrapper of the queue for the common in-memory queue.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 19, 2010
 */
public interface Destination {

	public Queue<Object> getQueue(String queueName);
	
}
