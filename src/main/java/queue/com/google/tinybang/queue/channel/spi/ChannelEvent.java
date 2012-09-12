/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

import com.google.tinybang.queue.channel.event.EventHeader;

/**
 * The event supports to represents the tasks.
 * <p> All the data is event driven, so the ServerStartEvent, MessageEvent, and OnExceptionEvent are all defined.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public interface ChannelEvent<T> extends Cloneable {
	/**
	 * Object of the message which will be serialized to string for the message
	 * transfer.
	 * <p>
	 * If there is some exceptions during the serialization, an error will be
	 * thrown out, if you want to print the detail messages, please override the
	 * toString methods.
	 * */
	public T getMessage();

}
