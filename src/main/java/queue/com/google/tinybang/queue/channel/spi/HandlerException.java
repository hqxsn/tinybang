/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

/**
 * Supports the exception during the handling.
 * <p>
 * retry function is defined for this exception, if the exception happened, will
 * be retry according the configuration of the <code>getRetryTimes()</code> of
 * the {@link ChannelConfig}.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Sep 7, 2010
 */
public class HandlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4365885095461532207L;

	public HandlerException() {
		super();
	}

	public HandlerException(String message, Throwable cause) {
		super(message, cause);
	}

	public HandlerException(String message) {
		super(message);
	}

	public HandlerException(Throwable cause) {
		super(cause);
	}

	
	
}
