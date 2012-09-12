/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.event;

import com.google.tinybang.queue.channel.spi.Channel;
import com.google.tinybang.queue.channel.spi.ChannelEvent;

/**
 * Supports the connection and re-connection.
 * <p> Start a connection, Close a connection, and heartbeat :)
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 18, 2010
 */
public class ServerStateEvent implements ChannelEvent<ServerStateEvent.STATE> {

	private Channel jmsChannel;

	private STATE state;
	
	private EventHeader header;
	
	

	public ServerStateEvent(Channel jmsChannel, STATE state) {
		super();
		this.jmsChannel = jmsChannel;
		this.state = state;
	}

	public STATE getState() {
		return this.state;
	}
	
	public Channel getChannel() {
		return this.jmsChannel;
	}

	public enum STATE {
		OPEN,CLOSE,CONNECTED, RECONNETED
	}


    @Override
    public STATE getMessage() {
        return getState();
    }

}
