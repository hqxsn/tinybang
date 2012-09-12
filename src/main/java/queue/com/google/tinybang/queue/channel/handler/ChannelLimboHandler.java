/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler;

/**
 * <tt>Limbo</tt> as the end-result of all the channel messages if they are lost
 * during the producing.
 * <p>
 * If the message cannot go to the queue after re-tried a lot of times, the
 * message will go to Limbo and waiting for the dead.
 * <p>
 * Every {@link com.google.tinybang.queue.channel.spi.Channel} has one Limbo, and the objects will go to this limbo if
 * cannot be processed.
 * <p>
 * Please extends this handler to handler the limbo tasks, if you do not
 * configure the Limbo tasks, the message will be lost. you can persist them to
 * the database via under taker.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a> Oct 19,
 *         2010
 */
public interface ChannelLimboHandler extends DownStreamHandler {

	
}
