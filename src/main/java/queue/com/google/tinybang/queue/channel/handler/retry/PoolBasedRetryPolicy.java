/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.handler.retry;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.handler.SimpleDownStreamHandler;
import com.google.tinybang.queue.channel.spi.ChannelEvent;
import com.google.tinybang.queue.channel.spi.HandlerContext;
import com.google.tinybang.queue.channel.spi.HandlerException;

/**
 * Supports to put the data into the queue and then another daemon thread will process the event.
 * <p> the handler is created and used per queue.
 * <p> Currently, this policy is not used.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Oct 18, 2010
 */
public class PoolBasedRetryPolicy extends SimpleDownStreamHandler {
	
	private static BlockingQueue<RetryTask> _retry_tasks = new LinkedBlockingQueue<RetryTask>();


    static {
		DeamonThread thread = new DeamonThread();
		thread.run();
	}
	
	/**
	 * 
	 * */
	private static class DeamonThread extends Thread {

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			RetryTask task = null;
			try {
				while ((task = _retry_tasks.take()) != null) {
					if (task != null)
						try {
							task.getCtx().sendDownstream(task.getEvent());
						} catch (HandlerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
	public void handleDownstream(HandlerContext ctx, ChannelEvent e) throws HandlerException {
		RetryTask task = new RetryTask(ctx, e);
		_retry_tasks.add(task);
	}
	
	/**
	 * A wrapper for the retry tasks.
	 * */
	private static class RetryTask {
		
		private HandlerContext ctx;
		
		private ChannelEvent event;

		public RetryTask(HandlerContext ctx, ChannelEvent event) {
			super();
			this.ctx = ctx;
			this.event = event;
		}

		/**
		 * @return the ctx
		 */
		public HandlerContext getCtx() {
			return ctx;
		}

		/**
		 * @return the event
		 */
		public ChannelEvent getEvent() {
			return event;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RetryTask [ctx=" + ctx + ", event=" + event + "]";
		}
		
	}


	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.handler.SimpleDownStreamHandler#handle(com.starcite.marketplace.channel.spi.HandlerContext, com.starcite.marketplace.channel.event.MessageEvent)
	 */
	@Override
	protected void handle(HandlerContext ctx, MessageEvent e) throws HandlerException {
		throw new IllegalStateException("Do not use this method here, override the handleDownstream method to handle the retry!");

	}

}
