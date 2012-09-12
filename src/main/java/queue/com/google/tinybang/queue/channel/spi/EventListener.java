/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;


/**
 * Supports to handle message accordingly.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Sep 21, 2010
 */
public interface EventListener<T> {
	/**
	 * Handle the message according the incoming the data.
	 * <p> If you want to use the listener as the {@link Actor} pattern, the T supports the input data.
	 * 
	 * */
	public void onMessage(T t);
	
}
