/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.sink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.tinybang.CommonLogger;
import com.google.tinybang.Utils;
import com.google.tinybang.queue.channel.Channels;
import com.google.tinybang.queue.channel.event.ChannelMessageWrapper;
import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.spi.Channel;
import com.google.tinybang.queue.channel.spi.ChannelConfig;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * Supports to execute the worker tasks for the event handler.
 * <p>
 * Worker also handle the retry function
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a> Jun 20,
 *         2010
 */
public class Worker implements Runnable {

	private Channel channel;

	private AtomicBoolean isAync = new AtomicBoolean();

	private BlockingQueue<ChannelMessageWrapper> messages = new LinkedBlockingQueue<ChannelMessageWrapper>();

	private String workerName;

	private CommonLogger consoleLogger = new CommonLogger(
			ServerChannelSink.class.getSimpleName());

	private AtomicBoolean isRunning = new AtomicBoolean(true);

	private Object obj = new Object();
	
	private boolean isBatch;
	
	private int batchSize;

	public void stop() {
		isRunning.set(false);
	}

	Worker(ExecutorService executor, String workerName) {
		this.workerName = workerName;
	}

	public void bind(ChannelConfig channelConfig, Channel channel) {
		this.isBatch = channelConfig.isBatch();
		this.batchSize = channelConfig.getBatchSize();
		this.isAync.set(channelConfig.isAync());
		this.channel = channel;
		if (isAync.get()) {
			Thread thread = new Thread(this);
			thread.setName(this.getName());
			thread.start();
		}
	}

	public void register(ChannelMessageWrapper message) {
		if (isAync.get()) {
			messages.add(message);
		} else if (isBatch) {
			if (messages.size() < batchSize) {
				messages.add(message);
			} else {
                messages.add(message);
				for (int i =0; i < batchSize && !messages.isEmpty(); i ++) {
					ChannelMessageWrapper aMessage = messages.poll();
					fireMessageReceive(aMessage);
				}
			}
		} else {
			fireMessageReceive(message);
		}
	}

	@Override
	public String toString() {
		return this.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		while (isRunning.get()) {
			if (!messages.isEmpty()) {
				ChannelMessageWrapper object = messages.poll();
				if (null != object) {
					try {
						fireMessageReceive(object);
					}catch (Throwable exception) {
						consoleLogger.error("Exception happen during the message receive, the exception is " + Utils.outputStackTrace(exception));
					}
				}
			} else {
				try {
                    synchronized (obj) {
                        obj.wait(500l);
                    }

				} catch (InterruptedException e) {
					// TODO: Handle the exception here.
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param message
	 */
	private void fireMessageReceive(ChannelMessageWrapper message) {
		long start = System.currentTimeMillis();
		message.setUpStreamTime(start);
		try {
			Channels.fireMessageReceived(this.channel, message);
		} catch (HandlerException e) {
			consoleLogger.error(Utils.outputStackTrace(e));
            Channels.retry(channel, (MessageEvent)message.getMessage());
		} catch (Throwable thr) {
            consoleLogger.error(Utils.outputStackTrace(thr));
            Channels.retry(channel, (MessageEvent)message.getMessage());
        }
		consoleLogger.info(this.getName() + " " + message);
	}

	public String getName() {
		return this.workerName;
	}

}