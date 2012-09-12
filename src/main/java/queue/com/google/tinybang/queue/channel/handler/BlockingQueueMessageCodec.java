package com.google.tinybang.queue.channel.handler;

import com.google.tinybang.queue.channel.event.ChannelMessageWrapper;
import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * Supports for the blocking queue usage for the channels.
 * <p> can be defined as the in-jvm queue other than the cross-jvm queue.
 * <p> Can be use as the in-jvm Actor Pattern.
 * 
 * Created by TinyBang
 * User: Wenzhong
 * Date: Aug 12, 2010
 */
public class BlockingQueueMessageCodec extends SimpleUpStreamHandler implements DownStreamHandler {

    @Override
    protected void messageReceived(HandlerContext ctx, ChannelEvent e) throws HandlerException {

    }
    
	@Override
	public void handleUpstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {

	}    
}
