/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

import com.google.tinybang.queue.channel.event.OnExceptionEvent;

/**
 * An interface for the exception listener, please simply implement this listener.
 * <p> Technical, Please use the exception listener to handle the exception instead of the common exception handling logic.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Oct 21, 2010
 */
public interface ExceptionListener {
	
	/**
	 * Supports to process the exception if there is any happen in the Channel.
	 * */
	public void onException(HandlerContext context, OnExceptionEvent event);
	
}
