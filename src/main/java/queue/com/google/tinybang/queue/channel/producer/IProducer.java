/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.producer;

import com.google.tinybang.queue.channel.event.MessageEvent;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 12, 2010
 */
public interface IProducer {

	public void produce(MessageEvent object);
	
	public boolean isValid();
	
	public boolean reload();
	
	public boolean disable();
	
}
