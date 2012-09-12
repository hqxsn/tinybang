/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;


/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 20, 2010
 */
public interface IQueueBootstrap {
	
	public IQueueBootstrap add(String queue);
	
	public void run();

	public void stop();
}
