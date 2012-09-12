/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.sink;

import com.google.tinybang.CommonLogger;
import com.google.tinybang.SystemExitHelper;
import com.google.tinybang.Utils;
import com.google.tinybang.queue.channel.config.Mode;
import com.google.tinybang.queue.channel.consumer.IConsumer;
import com.google.tinybang.queue.channel.consumer.IConsumerFactory;
import com.google.tinybang.queue.channel.consumer.impl.BlockingQueueConsumerFactory;
import com.google.tinybang.queue.channel.event.ChannelMessageWrapper;
import com.google.tinybang.queue.channel.event.ServerStateEvent;
import com.google.tinybang.queue.channel.spi.*;
import com.tinybang.lb.chh.ConsistentHashing;
import com.tinybang.lb.chh.MD5HashFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public class ServerChannelSink implements ChannelSink {

	private static final String _JMS_CONSUMER_FACTORY = System.getProperty("message.queue.consumer.factory");
		
	/**
	 * Worker list supports to handle the consumer and process.
	 */
	private Worker[] workers;
	
	private int _worker_count;
		
    private final AtomicInteger workerIndex = new AtomicInteger();



    private ChannelConfig channelConfig;
    
    private IConsumerFactory consumerFactory;
    
    private volatile boolean isRunning = true; 
    
    private CommonLogger consoleLogger = new CommonLogger(ServerChannelSink.class.getSimpleName());

    private ConsistentHashing<String> hashing = null;
	/**
	 * TODO: Handle the exception
	 * @param channelConfig
	 */
	public ServerChannelSink(ChannelConfig channelConfig) {
		this.channelConfig = channelConfig;
		_worker_count = channelConfig.getWorkerCount();
		workers = new Worker[channelConfig.getWorkerCount()];

        Collection<String> workNames = new ArrayList<String>();
        for (int i = 0; i < channelConfig.getWorkerCount(); i ++) {
			Worker worker = new Worker(Executors.newFixedThreadPool(10), "[" + channelConfig.getQueue() + "]: [Worker]: " + i);
			workers[i] = worker;
            workNames.add(worker.getName());
		}

        hashing = new ConsistentHashing<String>(new MD5HashFunction(),  workNames.size(),
                workNames);

		String consumerFactoryClass = null;
		try {
			if (channelConfig.getMode() == Mode.JMS) {
				consumerFactoryClass = _JMS_CONSUMER_FACTORY;
			} else if (channelConfig.getMode() == Mode.BLOCKINGQUEUE){
				consumerFactoryClass = BlockingQueueConsumerFactory.class.getName();
			} else {
				throw new IllegalArgumentException("please set the correct mode for the channel!");
			}
			consumerFactory = (IConsumerFactory) Class.forName(consumerFactoryClass).newInstance();
		} catch (Exception e) {
			/*Cannot start the server, will send the escalation and stop directly*/
			SystemExitHelper.exit("Cannot init the consumer according the configuration, please check the HornetQ setup", e);
		} 
		
	}

	/* 
	 * Currently, simply use the worker.register to start the worker and handle the consumer process.
	 * Still thinking how to start more consumer.
	 * @see com.starcite.marketplace.channel.spi.ChannelSink#eventSunk(com.starcite.marketplace.channel.spi.ChannelPipeline, com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public ChannelFuture eventSunk(ChannelPipeline channelPipeline) {
		return bind(channelPipeline.getChannel());
	}

	/**
	 * @param channel
	 */
	private ChannelFuture bind(Channel channel) {

		for (Worker worker: workers) {
			worker.bind(channelConfig, channel);
		}
		//Start Consumer here
		Boss[] bosses = new Boss[channelConfig.getConsumerCount()];
		
		for (int i = 0; i < channelConfig.getConsumerCount(); i ++) {
			bosses[i] = new Boss(channel, "[" + channelConfig.getQueue() + "]: [Boss]: " + i, i);
		}
		
		for (Boss boss: bosses) {
			Thread thread = new Thread(boss);
			thread.setName(boss.bossName);
			thread.start();
		}

        return new ChannelFuture() {
            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public boolean isCancel() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

	}
	
	public class Boss implements Runnable {
		
		public String bossName;
		
        private int bossId;
       
		public Boss(Channel channel, String bossName, int bossId) {
			this.bossName = bossName;
            this.bossId = bossId;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			consoleLogger.info("starting geting the consumer " + this.bossName + " " + channelConfig.getQueue());
			IConsumer consumer = consumerFactory.getConsumer(channelConfig.getQueue());
			consoleLogger.info("finished find the consumer " + this.bossName + " " + channelConfig.getQueue());
			try {
				while (isRunning) {
					try {
						if (consumer.isValid()) {
							Object object = null;
							try {
								object = consumer.receive(500l);
							} catch (ConsumerException exception) {
								// the consumer is closed, just for the double check!
								if (IConsumer._consumer_close_error_message.equalsIgnoreCase(exception.getMessage())) {
									//Simply update the consumer to the invalid.
									consumer.disable();
									consoleLogger.info("Disable current consumer and will reload a new one during the next loop [" + exception.getMessage() + "]" + Utils.outputStackTrace(exception));
									continue;
								} else {
									consoleLogger.info("Exception happen during the consumer receive message[" + exception.getMessage() + "] " + Utils.outputStackTrace(exception));
								}
								
							}
							if (object != null) {
								fireMessageReceive(object, bossId);
							}
						} else {
							consoleLogger.info("starting geting a new consumer " + this.bossName + " " + channelConfig.getQueue());
							consumer.reload(channelConfig.getQueue());
							consoleLogger.info("finished get the new consumer " + this.bossName + " " + channelConfig.getQueue());
						}
					} catch (Throwable e) {
						//Currently, cache all the exceptions and make sure they are all working.
//						consoleLogger.logError(Utils.outputStackTrace(e));
					}

				}
			} finally {
				consumer.close();
			}

		}

		/**
		 * Compose a new wrapper for the message for the logging and routing.
		 * @param message
		 */
		private void fireMessageReceive(Object message, int bossId) {
			
			int messageId = getMessageId();
			
			Worker worker = nextWorker(messageId);
			
			ChannelMessageWrapper channelMessage = null;
			
			if (channelConfig.getMode() == Mode.JMS) {
			
//				TextMessage textMessage = (TextMessage) message;
//
//				Message aMessage = new Message();
//
//				try {
//					String headerText = textMessage.getStringProperty(MessageHeader.MESSAGEHEADER);
//					MessageHeader messageHeader = MessageHelper.deSerializeMessageHeader(headerText);
//					messageHeader.setReceivedNanoTime(System.nanoTime());
//					messageHeader.setReceivedDate(new Date());
//
//                    //Here introduce sticky load balance
//                    if(!Utils.isNullOrEmpty(messageHeader.getLbFactor()))
//                        worker = lbNextWorker(messageHeader.getLbFactor(), worker);
//
//					String messageBodyText = textMessage.getText();
//					String messageBodyContentMetaDataText = textMessage.getStringProperty(MessageBody.MESSAGE_BODY_CONTENT_METADATA);
//					MessageBodyContentMetaData bodyContentMetaData = MessageHelper
//							.deSerializeMessageBodyContentMetaData(messageBodyContentMetaDataText);
//
//					Object object = MessageHelper.deSerializeMessageBodyContent(messageBodyText, bodyContentMetaData);
//
//					aMessage.setMessageHeader(messageHeader);
//					MessageBody messageBody = new MessageBody();
//					messageBody.setMessageBodyContent(object);
//					aMessage.setMessageBody(messageBody);
//
//					channelMessage = new ChannelMessageWrapper(
//							channelConfig.getQueue(), messageId % _worker_count, bossId,
//							System.currentTimeMillis(), 0, aMessage, messageHeader.getMessageId());
//                    channelMessage.setWorkerName(worker.getName());
//
//				} catch (JMSException e) {
//					consoleLogger.logError(Utils.outputStackTrace(e));
//				} catch (ToStringException e) {
//					consoleLogger.logError(Utils.outputStackTrace(e));
//				}
//
				worker.register(channelMessage);
			} else {
//				Message messageObj = (Message)message;
//
//				messageObj.getMessageHeader().setReceivedNanoTime(System.nanoTime());
//				messageObj.getMessageHeader().setReceivedDate(new Date());
//
//				channelMessage = new ChannelMessageWrapper(
//						channelConfig.getQueue(), messageId % _worker_count, bossId,
//						System.currentTimeMillis(), 0, message, String.valueOf(messageId));
//				worker.register(channelMessage);
//			}
//
//			consoleLogger.logInfo("Message " + channelMessage.toString());
            }
		}
		
		/**
		 * @return an unique message id for the routing.
		 */
		private int getMessageId() {
			int i = workerIndex.incrementAndGet();
			if (i >= 214783646) {
				workerIndex.set(0);
				i = workerIndex.incrementAndGet();
			}
			return i;
		}

		private Worker nextWorker(int id) {
			return workers[id %_worker_count];
		}

        private Worker lbNextWorker(String lbFactor, Worker defaultWorker) {
            String workName = hashing.get(lbFactor);
			for(Worker worker:workers) {
                if(worker.getName().equals(workName)) {
                    return worker;
                }
            }
            return defaultWorker;
		}
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelSink#stop()
	 */
	@Override
	public boolean stop() {
		isRunning = false;
		for (Worker worker: workers) {
			worker.stop();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.starcite.marketplace.channel.spi.ChannelSink#eventSunk(com.starcite.marketplace.channel.spi.ChannelPipeline, com.starcite.marketplace.channel.spi.ChannelEvent)
	 */
	@Override
	public ChannelFuture eventSunk(ChannelPipeline pipeline, ChannelEvent event) {
		if (event instanceof ServerStateEvent) {
			if (((ServerStateEvent) event).getState() == ServerStateEvent.STATE.CLOSE) {
				stop();
			}
		}
        return null;
	}
	
}
