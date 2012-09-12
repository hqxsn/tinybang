/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.event;

import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * An exception event supports for the exception handling.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Oct 21, 2010
 */
public class OnExceptionEvent implements ChannelEvent<HandlerException> {

	private HandlerException exception;

    // Also track which event throws the exception, put the data here~
    private ChannelEvent event;

	public OnExceptionEvent(HandlerException exception, ChannelEvent event) {
		super();
		this.exception = exception;
		this.event = event;
	}


    @Override
    public HandlerException getMessage() {
        return this.exception;
    }
}
