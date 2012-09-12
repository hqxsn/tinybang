/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.pipeline;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.tinybang.CommonLogger;
import com.google.tinybang.Utils;
import com.google.tinybang.queue.channel.Channels;
import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.event.OnExceptionEvent;
import com.google.tinybang.queue.channel.handler.DownStreamHandler;
import com.google.tinybang.queue.channel.handler.UpStreamHandler;
import com.google.tinybang.queue.channel.spi.*;

/**
 * A simple pipeline implementation supports for the hanlder management.
 * <p>
 * supports add, remove, and load the handler accordingly and using an Linked
 * List to handle the relationship cross the handler.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a> Jun 17,
 *         2010
 */
public class CommonPipeline extends ChannelPipeline {
	
	private static CommonLogger _console_logger = new CommonLogger(CommonPipeline.class.getSimpleName());
	
	private Channel channel;
		
	private HandlerContext head;
	
	private HandlerContext tail;
	
	private HandlerContext limbo;
	
	public ConcurrentMap<String, HandlerContext> handlers = new ConcurrentHashMap<String, HandlerContext>(); 
	
	
	
	
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelPipeline#addLast(java.lang.String, com.starcite.marketplace.channel.spi.ChannelHandler)
	 */
	@Override
	public void addLast(String identifier, ChannelHandler channelHandler) {
		
		if (handlers.containsKey(identifier)) {
			throw new IllegalArgumentException("The handler already exsit in the pipeline, please check the handler setup!");
		}
		
		if (handlers.isEmpty()) {
			head = new DefaultChannelContext(null, null, identifier, channelHandler);
			tail = head;
			handlers.put(identifier, head);
			return;
		}
		
		HandlerContext current = tail;
		
		HandlerContext newTail = new DefaultChannelContext(current, null, identifier, channelHandler);
		
		current.setNextHandlerContext(newTail);
		
		tail = newTail;
		
		handlers.put(identifier, tail);
		
	}
	
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelPipeline#sendDownStream(com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public void sendDownStream(ChannelEvent channelEvent) {
		sendDownStream(this.head, channelEvent);
	}

	/**
	 * @param handlerContext
	 * @param channelEvent
	 */
	private void sendDownStream(HandlerContext handlerContext,ChannelEvent channelEvent) {
		HandlerContext aHandlerContext = getActualDownStreamContext(handlerContext);
		
		if (aHandlerContext != null) {
			try {
				aHandlerContext.getHandler().handleDownstream(aHandlerContext, channelEvent);
			} catch (HandlerException e) {
				handleException(aHandlerContext, channelEvent, e);
				_console_logger.error(MessageFormat.format("Exception happending during channel handler, the channel event is {0}", channelEvent), e);
			}
		} else {
			this.channel.getChannelSink().eventSunk(this, channelEvent);
		}
	}

	/**
	 * @param aHandlerContext
	 * @param channelEvent
	 * @param e
	 */
	private void handleException(HandlerContext aHandlerContext, ChannelEvent channelEvent, HandlerException e) {
		
//		channelEvent.getEventHeader().setException(e);
		/**Firstly handle the exception by the handler fistly.*/
		if (aHandlerContext.getHandler() instanceof ExceptionListener) {
			ExceptionListener listener = (ExceptionListener) aHandlerContext.getHandler();
			listener.onException(aHandlerContext, new OnExceptionEvent(e, channelEvent));
		}
//        else if (channelEvent.getEventHeader().getExceptionListener() != null) {
//			channelEvent.getEventHeader().getExceptionListener().onException(aHandlerContext, new OnExceptionEvent(e, channelEvent));
//		}
        else {
			Channels.retry(channel, (MessageEvent) channelEvent);
		}
	}

	/**
	 * @param context
	 * @return
	 */
	private HandlerContext getActualDownStreamContext(HandlerContext context) {
		
		if (context == null) {
			return null;
		}
		if (context.canHandleDownStream()) {
			return context;
		} else {
			context = context.next();
			if (context == null)
				return null;
			return getActualDownStreamContext(context);
		}
	}

	/**
	 * @param context
	 * @return
	 */
	private HandlerContext getActualUpStreamContext(HandlerContext context) {
		
		if (context == null) {
			throw new IllegalArgumentException("No context can handle the up stream context!");
		}
		if (context.canHandleUpStream()) {
			return context;
		} else {
			context = context.next();
			if (context == null)
				return null;
			return	getActualUpStreamContext(context);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelPipeline#sendUpStream(com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public void sendUpStream(ChannelEvent channelEvent) {
		sendUpStream(this.head, channelEvent);
	}
	
	public void sendUpStream(HandlerContext handlerContext, ChannelEvent channelEvent) {
		
		HandlerContext aHandlerContext = getActualUpStreamContext(handlerContext);
		
		if (aHandlerContext != null)
			try {
				handlerContext.getHandler().handleUpstream(aHandlerContext, channelEvent);
			} catch (HandlerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}


	public Channel getChannel() {
		return channel;
	}

	public class DefaultChannelContext implements HandlerContext {

		private String handlerName;
		
		private ChannelHandler channelHandler;
		
		HandlerContext next;
		
		HandlerContext pre;
		
		boolean canHandleUpStream;
		
		boolean canHandleDownStream;
		
		
		
		/**
		 * @param pre
		 * @param next
		 * @param identifier
		 * @param channelHandler
		 */
		public DefaultChannelContext(HandlerContext pre, HandlerContext next,
				String identifier, ChannelHandler channelHandler) {
			setHandlerName(identifier);
			this.channelHandler = channelHandler;
			this.pre = pre;
			this.next = next;
			if (channelHandler instanceof UpStreamHandler) {
				this.canHandleUpStream = true;
				this.canHandleDownStream = false;
			} else if (channelHandler instanceof DownStreamHandler) {
				this.canHandleUpStream = false;
				this.canHandleDownStream = true;
			} else {
				throw new IllegalArgumentException("Pleas make sure the handler implement the upstream handler of the down stream handler!");
			}
		}

		/**
		 * @param identifier
		 */
		private void setHandlerName(String identifier) {
			this.handlerName = identifier;
		}

		/* (non-Javadoc)
		 * @see com.starcite.marketplace.channel.spi.HandlerContext#sendUpstream(com.starcite.marketplace.channel.spi.ChannelEvent)
		 */
		@Override
		public void sendUpstream(ChannelEvent e) throws HandlerException {
			if (this.next != null) {
				CommonPipeline.this.sendUpStream(this.next, e);
			}
		}

		/* (non-Javadoc)
		 * @see com.starcite.marketplace.channel.spi.HandlerContext#getHandler()
		 */
		@Override
		public ChannelHandler getHandler() {
			return this.channelHandler;
		}

		/* (non-Javadoc)
		 * @see com.starcite.marketplace.channel.spi.HandlerContext#next()
		 */
		@Override
		public HandlerContext next() {
			return this.next;
		}

		public String getHandlerName() {
			return handlerName;
		}

		/* (non-Javadoc)
		 * @see com.starcite.marketplace.channel.spi.HandlerContext#setNextHandlerContext(com.starcite.marketplace.channel.spi.HandlerContext)
		 */
		@Override
		public void setNextHandlerContext(HandlerContext next) {
			this.next = next;
		}

		/* (non-Javadoc)
		 * @see com.starcite.marketplace.channel.spi.HandlerContext#getChannel()
		 */
		@Override
		public Channel getChannel() {
			return CommonPipeline.this.getChannel();
		}

		/* (non-Javadoc)
		 * @see com.starcite.marketplace.channel.spi.HandlerContext#canHandleUpStream()
		 */
		@Override
		public boolean canHandleUpStream() {
			return this.canHandleUpStream;
		}

		/* (non-Javadoc)
		 * @see com.starcite.marketplace.channel.spi.HandlerContext#canHandleDownStream()
		 */
		@Override
		public boolean canHandleDownStream() {
			return this.canHandleDownStream;
		}

		/* (non-Javadoc)
		 * @see com.starcite.marketplace.channel.spi.HandlerContext#sendDownstream(com.starcite.marketplace.channel.spi.ChannelEvent)
		 */
		@Override
		public void sendDownstream(ChannelEvent e) throws HandlerException {
			if (next != null) {
				CommonPipeline.this.sendDownStream(next, e);
			} else {
				CommonPipeline.this.sendDownStream(null, e);
			}
		}

		/**
		 * Kill the message, and the message will go to Limbo.
		 */
		@Override
		public boolean kill(ChannelEvent e) throws HandlerException {
			if (limbo == null) {
				// kick off and do nothing, just return back, because it is dead.
				return true;
			}
			sendDownStream(limbo, e);
			return true;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelPipeline#attach(com.starcite.marketplace.channel.spi.Channel, com.starcite.marketplace.channel.spi.ChannelSink)
	 */
	@Override
	public void attach(Channel channel) {
		this.channel = channel;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelPipeline#remove(java.lang.String)
	 */
	@Override
	public void remove(String identifier) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelPipeline#addLimbo(com.starcite.marketplace.channel.spi.ChannelHandler)
	 */
	@Override
	public void addLimboHandler(ChannelHandler channelHandler) {
		if (this.limbo != null) {
			// Handling the logging and still update the limbo.
		}
		this.limbo = new DefaultChannelContext(null, null, channelHandler.getClass().getSimpleName(), channelHandler);
	}
	
	

}
