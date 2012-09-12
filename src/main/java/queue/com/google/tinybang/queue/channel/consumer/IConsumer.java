/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.consumer;

import com.google.tinybang.queue.channel.spi.ConsumerException;

/**
 * An consumer wrapper for the MQ.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 2, 2010
 */
public interface IConsumer {
	/**
	 * Specify for the JMS Consumer.
	 * */
	public static final String _consumer_close_error_message = "javax.jms.IllegalStateException: Consumer is closed";

	public Object receive();
	
	public Object receive(long timeout) throws ConsumerException;
	
	public boolean isValid();
	
	/**
	 * Update to a new consumer.
	 * */
	public boolean reload(String queue);
	/**
	 * Simply Close the consumer with the connection.
	 * */
	public boolean close();
	/**
	 * Disable current consumer by set the valid as false.
	 * */
	public boolean disable();
	
}
