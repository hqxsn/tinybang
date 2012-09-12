/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;


/**
 * A {@link Channel} retry exception, the {@link com.google.tinybang.queue.channel.sink.ServerChannelSink} and
 * {@link com.google.tinybang.queue.channel.sink.Worker} will catch this exception to handle the retry.
 * <p>
 * Please send out this exception if you want to handle the retry, beyond that,
 * <li> Configure the retry policy in the {@link ChannelConfig} file, you can check the configurable policy as {@link com.google.tinybang.queue.channel.handler.retry.CommonDiscardRetryPolicy} and {@link com.google.tinybang.queue.channel.handler.retry.CommonPersistRetryPolicy} .
 * <li> also configure the retry times.
 * 
 * <p> ps: the default the retry times is 3 and the default policy is {@link com.google.tinybang.queue.channel.handler.retry.CommonLimboRetryPolicy}.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a> Oct 4, 2010
 */
public class RetryException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -930461263417563234L;

	public RetryException() {
		super();
	}

	public RetryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetryException(String message) {
		super(message);
	}

	public RetryException(Throwable cause) {
		super(cause);
	}

	
	
	
	
}
