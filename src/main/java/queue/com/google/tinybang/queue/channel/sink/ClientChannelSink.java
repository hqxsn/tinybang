/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.sink;

import com.google.tinybang.CommonLogger;
import com.google.tinybang.SystemExitHelper;
import com.google.tinybang.queue.channel.config.Mode;
import com.google.tinybang.queue.channel.event.MessageEvent;
import com.google.tinybang.queue.channel.event.ServerStateEvent;
import com.google.tinybang.queue.channel.producer.IProducer;
import com.google.tinybang.queue.channel.producer.IProducerFactory;
import com.google.tinybang.queue.channel.producer.impl.BlockingQueueProducerFactory;
import com.google.tinybang.queue.channel.spi.*;

import java.text.MessageFormat;
import java.util.Date;
/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 20, 2010
 */
public class ClientChannelSink implements ChannelSink {
	
	private static CommonLogger _logger = new CommonLogger(ClientChannelSink.class.getSimpleName());

	private static final String _JMS_PRODUCER_FACTORY = System.getProperty("message.queue.producer.factory");

	private IProducerFactory producerFactory;
	
	private ChannelConfig channelConfig;
	
	public ClientChannelSink(ChannelConfig channelConfig) {
		this.channelConfig = channelConfig;
		String producerFactoryClass = null;
		if (channelConfig.getMode() == Mode.JMS) {
			producerFactoryClass = _JMS_PRODUCER_FACTORY;
			_logger.info(MessageFormat.format("Using the producer factory {0}", _JMS_PRODUCER_FACTORY));
		} else if (channelConfig.getMode() == Mode.BLOCKINGQUEUE){
			producerFactoryClass = BlockingQueueProducerFactory.class.getName();
		} else {
			throw new IllegalArgumentException("please set the correct mode for the channel!");
		}
		try {
			this.producerFactory = (IProducerFactory) Class.forName(producerFactoryClass).newInstance();
		} catch (InstantiationException e) {
			SystemExitHelper.exit("Cannot create the producer factory according the configuration because of the instantiation, please check the exception ", e);
		} catch (IllegalAccessException e) {
			SystemExitHelper.exit("Cannot create the producer factory according the configuration because of the access, please check the exception ", e);
		} catch (ClassNotFoundException e) {
			SystemExitHelper.exit("Cannot create the producer factory according the configuration because of the class loader exception, please check the exception ", e);
		}

	}
	
	/** 
	 * Do not use this method now, it is useless due to current setup.
	 */
	@Override
	public ChannelFuture eventSunk(ChannelPipeline pipeline) {
		return eventSunk(pipeline, new ServerStateEvent(pipeline.getChannel(), ServerStateEvent.STATE.OPEN));
	}

	/** 
	 * Supports to produce the event to the queue.
	 */
	@Override
	public ChannelFuture eventSunk(ChannelPipeline pipeline, ChannelEvent event) {
		
		if (event instanceof ServerStateEvent) {
			ServerStateEvent sEvent = (ServerStateEvent)event;
			handleStateEvent(sEvent);
		}

		IProducer producer = null;
		try {
			producer = producerFactory.getProducer(channelConfig.getQueue());
			if (event instanceof MessageEvent)
				producer.produce((MessageEvent)event);
		} catch(Exception e) {
			_logger.error("Cannot handle the produce due to some exception, please check the server status.", e);
			// Currently, do not handle the exception here, will handle it in the upocoming release.
		} finally {
			if (producerFactory != null && producer != null) {
				producerFactory.returnProducer(channelConfig.getQueue(), producer);
			}
		}
        return new ChannelFuture() {
            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public boolean isCancel() {
                return false;
            }
        };
	}

	/**
	 * @param event
	 */
	private void handleStateEvent(ServerStateEvent event) {
		if (event.getState() == ServerStateEvent.STATE.CLOSE) {
			producerFactory.stop();
		}
		
	}

	/**
	 * @return whether need log the producer data into the profiling log (for
	 *         the log related data, need not log the profiling data)
	 */
	private boolean needLog() {
		boolean needLog = true;
		String queue = this.channelConfig.getQueue();
		if (queue.endsWith("LogQueue"))
			needLog = false;
		return needLog;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelSink#stop()
	 */
	@Override
	public boolean stop() {
		return true;
	}

}
