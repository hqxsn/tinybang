/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler.undertaker;

import com.google.tinybang.CommonLogger;
import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.handler.ChannelLimboHandler;
import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * Support to persist the message into the database for the further usage.
 * <p> Persist the data into the database for the further usage.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Oct 20, 2010
 */
public class CommonFailedMessageUndertaker implements ChannelLimboHandler {

	private static final String _MESSAGE_PERSIST_QUERY = "INSERT INTO TRAP_MESSAGES (QUEUE, SNAPSHOT, ERRORMESSAGE,CREATEDDATE) VALUES (?,?,?,?)";
	
	private static CommonLogger _log = new CommonLogger(
			CommonFailedMessageUndertaker.class.getSimpleName());
	
	/*
	 * Supports to provide an simple name for the handler which should be unique per message.
	 */
	@Override
	public String getName() {
		return CommonFailedMessageUndertaker.class.getSimpleName();
	}

	/* 
	 * Supports to persist the data into the database.
	 */
	@Override
	public void handleDownstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {
		
		if (e instanceof MessageEvent) {
			
			MessageEvent me = (MessageEvent)e;
			Object obj = e.getMessage();

        }
		

	}

	/* 
	 * Need not use this method because it is an downstream handler.
	 */
	@Override
	public void handleUpstream(HandlerContext ctx, ChannelEvent e)
			throws HandlerException {
		throw new IllegalStateException("Do not use this method, this handler is an downstream handler!");
	}

}
