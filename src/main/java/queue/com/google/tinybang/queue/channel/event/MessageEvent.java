/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.event;

import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.ExceptionListener;

/**
 * A common message event for all the real data handing.
 * <p> The event is working with the EventHeader for the retry and data transfer timestamp trigger.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 19, 2010
 */
public class MessageEvent<T> implements ChannelEvent<T> {
	/*the message is get from the channel, just simply move to here*/
	private T message;

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    /**
	 * Simply clone a new message event according the legacy one.
	 * <p> set the message as the new object for the deeply clone.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		MessageEvent messageEvent = (MessageEvent)super.clone();
		/*Simply create a new object for the message*/
		messageEvent.setMessage(null);
		return messageEvent;
	}

	@Override
	public String toString() {
		return message.toString();
	}

}
