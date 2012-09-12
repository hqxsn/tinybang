/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

/**
 * Handler Context supports to transfer the data between the upstream handling and downstream handling.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public interface HandlerContext {

	/**
	 * Supports to call the next upstream handler to handle the message.
	 * @param e represents the event messages.
	 * @throws HandlerException if there is anything wrong. retry policy and message handler will support to handle it.
	 * */
    void sendUpstream(ChannelEvent e) throws HandlerException;

	/**
	 * Supports to call the next downstream handler to handle the message.
	 * @param e represents the event messages.
	 * */
    void sendDownstream(ChannelEvent e) throws HandlerException;
    
    /**
     * Get the current handler.
     * */
    ChannelHandler getHandler();
    /**
     * Get the next context, the contexts are all binding together with the LinkedList.
     * */
	HandlerContext next();
	
	/**
	 * Set the next handler, just setup the context, add it into the linkedList.
	 * */
	void setNextHandlerContext(HandlerContext next);
	
	/**
	 * Supports to get the channel binding with the context.
	 * */
	Channel getChannel();
	/**
	 * Two type handler are defined (upstream and downstream), that is for the upstream.
	 * */
	boolean canHandleUpStream();
	
	/**
	 * Check whether it can handle downstream messages.
	 * */
	boolean canHandleDownStream();
	
	/**
	 * Kill the dreaming message and put it into the Limbo.
	 * */
	boolean kill(ChannelEvent e) throws HandlerException;
	
}
