/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

/**
 * Supports for the aync tasks.
 * <p> the return of the channel method.
 * <p> supports to check the status of the action, and also can support to add listeners to handle the exceptions.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 14, 2010
 */
public interface ChannelFuture {
	
	public boolean isDone();
	
	public boolean isCancel();

}
