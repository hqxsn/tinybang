/**  Copyright [2011] TinyBang

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.google.tinybang.queue.channel.common;

import com.google.tinybang.queue.channel.config.Mode;
import com.google.tinybang.queue.channel.spi.ChannelConfig;

import java.util.List;

/**
 * Supports the default channel configuration for the undefined queue.
 *
 * <p> Put some default configuration, if want to make it diff, please configure yourself in the <tt>ChannelConfig</tt>
 * <p> This configuration is not only contains the client side, but also with the server side configuration, in the server side, you can working with our own defined workers, also you can send the message to your container directly.
 *
 * Created by TinyBang
 * User: wenzhong
 * Date: 2/27/11
 * Time: 11:20 AM
 */
public class CommonChannelConfig implements ChannelConfig {

    // Make it configurable in future.
    private static final int _default_retry_times = 3;

	private String queue;

	private int consumerCount;

	private int workerCount;

	private Mode mode = Mode.BLOCKINGQUEUE;

	private List<Peer> handlers;
	/*default set it as true, only customized for the special queue*/
	private boolean isAync = true;

	private boolean isBatch;

	private int batchSize;

	private int retryTimes = 3;
	/*default undertaker*/
	private String undertaker = "com.starcite.marketplace.channel.handler.undertaker.CommonFailedMessageUndertaker";

    @Override
    public int getDefaultRetryTimes() {
        return _default_retry_times;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public int getConsumerCount() {
        return consumerCount;
    }

    public void setConsumerCount(int consumerCount) {
        this.consumerCount = consumerCount;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    public void setWorkerCount(int workerCount) {
        this.workerCount = workerCount;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public List<Peer> getHandlers() {
        return this.handlers;
    }

    @Override
    public void setHandlers(List<Peer> handlers) {
        this.handlers = handlers;
    }

    public boolean isAync() {
        return isAync;
    }

    public void setAync(boolean aync) {
        isAync = aync;
    }

    public boolean isBatch() {
        return isBatch;
    }

    public void setBatch(boolean batch) {
        isBatch = batch;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getUndertaker() {
        return undertaker;
    }

    public void setUndertaker(String undertaker) {
        this.undertaker = undertaker;
    }
}
