/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel;

import com.google.tinybang.CommonLogger;
import com.google.tinybang.SystemExitHelper;
import com.google.tinybang.Utils;
import com.google.tinybang.queue.channel.config.ChannelConfigHolder;
import com.google.tinybang.queue.channel.factory.CommonServerChannelFactory;
import com.google.tinybang.queue.channel.pipeline.CommonPipeline;
import com.google.tinybang.queue.channel.spi.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A helper class to create an server side channel.
 * 
 * <p> Simply for the server side to handle the binder and config.
 *  
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 19, 2010
 */
public class ServerBootstrap implements IQueueBootstrap {
	
	private static CommonLogger _log = new CommonLogger(ServerBootstrap.class.getSimpleName());

	private static List<String> queues = new ArrayList<String>();

	private static Map<String, Channel> channels = new HashMap<String, Channel>();
	
	public ServerBootstrap() {};
	
	public ServerBootstrap add(String queue) {
		queues.add(queue);
		return this;
	}
	
	private static ServerBootstrap _server_boot_strap = new ServerBootstrap();
	
	public static ServerBootstrap getInstance() {
		return _server_boot_strap;
	}

	public void run() {
		for (String queue: queues) {
			ChannelConfig config = ChannelConfigHolder.getInstance().getConfig(queue);
            if (config == null) {
                // Cannot find the config according the configuration file.
                SystemExitHelper.exit(MessageFormat.format("Cannot find the configuration for the queue {0}, please check the configuration file channelConfig!", queue));
            }
			ChannelFactory channelFactory = new CommonServerChannelFactory(config);
			ChannelPipeline workerPipeline = new CommonPipeline();
			for (ChannelConfig.Peer handler: config.getHandlers()) {
				try {
					ChannelHandler aChannelhandler = (ChannelHandler)Class.forName(Utils.trim(handler.getHandlerName())).newInstance();
					
					workerPipeline.addLast(aChannelhandler.getClass().getSimpleName(), aChannelhandler);
					
				} catch (Exception e) {
					SystemExitHelper
							.exit(
									"Cannot start the channel, please check the configuration of the setting of the handler ["
											+ handler.getHandlerName()
											+ "], will stop the system with the escalation",
									e);
				}
			}
			

			Channel channel = channelFactory.newChannel(workerPipeline);
			
			channel.connect();

			channels.put(queue, channel);
		}
	}
	
	public void stop() {

		_log.info("Start stoping the HornetQ connection channel");

		for (String queue: queues) {
			Channel channel = channels.get(queue);
			try {
				Channels.fireStop(channel);
			} catch (HandlerException e) {
				_log.error("failed stop the channel queue [" + queue+ "]",e);
			}
		}
		_log.info("Finish stop the HornetQ connection channel");
	}

	/**
	 * @param queueName
	 */
	public Channel getChannel(String queueName) {
		return channels.get(queueName);
	}
}
