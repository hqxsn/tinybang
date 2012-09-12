/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel;

import com.google.tinybang.CommonLogger;
import com.google.tinybang.SystemExitHelper;
import com.google.tinybang.Utils;
import com.google.tinybang.queue.channel.common.CommonChannelConfig;
import com.google.tinybang.queue.channel.config.ChannelConfigHolder;
import com.google.tinybang.queue.channel.config.CommonServerChannelConfig;
import com.google.tinybang.queue.channel.factory.CommonClientChannelFactory;
import com.google.tinybang.queue.channel.handler.retry.CommonLimboRetryPolicy;
import com.google.tinybang.queue.channel.handler.undertaker.CommonFailedMessageUndertaker;
import com.google.tinybang.queue.channel.pipeline.CommonPipeline;
import com.google.tinybang.queue.channel.spi.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A helper class to start all the channels and connections together.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 *         Jul 20, 2010
 */
public class ClientBootstrap implements IQueueBootstrap {

    private static List<String> queues = new ArrayList<String>();

    private static ConcurrentMap<String, Channel> channels = new ConcurrentHashMap<String, Channel>();

    private static CommonLogger _log = new CommonLogger(ClientBootstrap.class.getSimpleName());

    private Map<String, CommonServerChannelConfig> configMapping;

    public static ClientBootstrap _client_boot_strap = new ClientBootstrap();

    public Lock channelLock = new ReentrantLock();

    /*Currently, use the pre init, will make it configurable*/
    private ClientBootstrap() {
        init();
    }

    ;

    /**
     * Supports to validate the configuration and get load the configuration files.
     */
    private void init() {
        this.configMapping = ChannelConfigHolder.getInstance().getConfigs();
        if (!isValid(this.configMapping)) {
            SystemExitHelper.exit("Cannot load the configuations from the configuration file please check the channelConfig.xml");
        }
    }

    /**
     * Validate the configuration here, currently, only handle the null check.
     *
     * @param configMapping2
     * @return
     */
    private boolean isValid(Map<String, CommonServerChannelConfig> configMapping2) {
        return configMapping2 != null;
    }

    public static ClientBootstrap getInstance() {
        return _client_boot_strap;
    }

    /**
     * load a channel and put the reference into the cache for the further usage.
     * @param queue
     * @return
     */
    public Channel getChannel(String queue) {
        try {
            channelLock.lock();
            if (!channels.containsKey(queue)) {
                start(queue);
            }
            return channels.get(queue);
        } finally {
            channelLock.unlock();
        }
    }

    public ClientBootstrap add(String queue) {
        queues.add(queue);
        return this;
    }

    public void removeHandler(String queue, ChannelHandler handler) {
        channels.get(queue).getPipeline().remove(handler.getName());
    }

    public void removeHandler(String queue, String handler) {
        channels.get(queue).getPipeline().remove(handler);
    }

    public void addHandler(String queue, ChannelHandler handler) {
        channels.get(queue).getPipeline().addLast(handler.getName(), handler);
    }

    public void start(String queue) {

        Channel channel = null;

        if (configMapping.containsKey(queue)) {
            // the configured queue, loading with the configuration handler, otherwise, use the common solutions.
            channel = composeConfiguredChannel(queue);
        } else {
            channel = composeDefaultChannel(queue);
        }

        if (channel == null)
            throw new IllegalStateException(MessageFormat.format("Cannot build the channel according the queue {0}, please check the configuration~", queue));

        channel.connect();

        channels.put(queue, channel);
    }

    private Channel composeDefaultChannel(String queue) {

        ChannelPipeline pipeline = new CommonPipeline();
        // Add the retry pipeline for the retry usage.
        ChannelHandler handler = new CommonLimboRetryPolicy();
        pipeline.addLast(handler.getName(), handler);
        CommonFailedMessageUndertaker undertaker = new CommonFailedMessageUndertaker();
        pipeline.addLimboHandler(undertaker);

        ChannelFactory channelFactory = new CommonClientChannelFactory(new CommonChannelConfig());
        Channel channel = channelFactory.newChannel(pipeline);

        return channel;
    }

    private Channel composeConfiguredChannel(String queue) {

        CommonServerChannelConfig config = configMapping.get(queue);
        ChannelFactory channelFactory = new CommonClientChannelFactory(config);
        ChannelPipeline workerPipeline = new CommonPipeline();

        boolean withRetryPolicy = false;

        for (ChannelConfig.Peer handler : config.getHandlers()) {
            try {
                if (handler.getType() == ChannelConfig.Type.DOWNSTREAM) {
                    ChannelHandler aChannelhandler = (ChannelHandler) Class.forName(Utils.trim(handler.getHandlerName())).newInstance();
                    workerPipeline.addLast(aChannelhandler.getClass().getSimpleName(), aChannelhandler);
                    if (handler.getHandlerName().contains(".retry.")) {
                        withRetryPolicy = true;
                    }
                }
            } catch (InstantiationException e) {
                SystemExitHelper.exit(
                        "InstantiationException happen during the initialize the channel handler "
                                + handler.getHandlerName(), e);

            } catch (IllegalAccessException e) {
                SystemExitHelper.exit(
                        "IllegalAccessException happen during the initialize the channel handler "
                                + handler.getHandlerName(), e);

            } catch (ClassNotFoundException e) {
                SystemExitHelper.exit(
                        "ClassNotFoundException happen during the initialize the channel handler "
                                + handler.getHandlerName(), e);

            }
        }

        if (!withRetryPolicy) {
            ChannelHandler handler = new CommonLimboRetryPolicy();
            workerPipeline.addLast(handler.getName(), handler);
        }

        ;

        ChannelHandler aChannelhandler = null;
        try {
            aChannelhandler = (ChannelHandler) Class.forName(Utils.trim(config.getUndertaker())).newInstance();
        } catch (InstantiationException e) {
            SystemExitHelper.exit(
                    "InstantiationException happen during the initialize the channel handler "
                            + config.getUndertaker(), e);
        } catch (IllegalAccessException e) {
            SystemExitHelper.exit(
                    "IllegalAccessException happen during the initialize the channel handler "
                            + config.getUndertaker(), e);
        } catch (ClassNotFoundException e) {
            SystemExitHelper.exit(
                    "ClassNotFoundException happen during the initialize the channel handler "
                            + config.getUndertaker(), e);
        }
        workerPipeline.addLimboHandler(aChannelhandler);

        Channel channel = channelFactory.newChannel(workerPipeline);

        return channel;
    }

    /* Stop the connections. etc.
      * @see com.starcite.marketplace.utils.IQueueBootstrap#stop()
      */
    @Override
    public void stop() {

        _log.info("Start stoping the HornetQ connection channel");
        for (String queue : queues) {
            Channel channel = channels.get(queue);
            try {
                Channels.fireStop(channel);
            } catch (HandlerException e) {
                _log.error("failed stop the channel queue [" + queue + "]", e);
            }
        }
        _log.info("Finish stop the HornetQ connection channel");

    }

    /* (non-Javadoc)
      * @see com.starcite.marketplace.utils.IQueueBootstrap#run()
      */
    @Override
    public void run() {
        // do nothing here.
    }
}
