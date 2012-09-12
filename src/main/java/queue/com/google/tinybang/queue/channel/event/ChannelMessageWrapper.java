/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.event;

/**
 * A simple wrapper class for the message for the message management.
 * 
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 22, 2010
 */
public class ChannelMessageWrapper {
	
	private String messageId;

	private String queue;
	
	private int worker;
	
	private int boss;
	
	private long consumerStartTime;
	
	private long upStreamTime;
	
	private Object message;

    private String workerName;

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public int getWorker() {
		return worker;
	}

	public void setWorker(int worker) {
		this.worker = worker;
	}

	public int getBoss() {
		return boss;
	}

	public void setBoss(int boss) {
		this.boss = boss;
	}

	public long getConsumerStartTime() {
		return consumerStartTime;
	}

	public void setConsumerStartTime(long consumerStartTime) {
		this.consumerStartTime = consumerStartTime;
	}

	public long getUpStreamTime() {
		return upStreamTime;
	}

	public void setUpStreamTime(long upStreamTime) {
		this.upStreamTime = upStreamTime;
	}

	public ChannelMessageWrapper(String queue, int worker, int boss,
			long consumerStartTime, long upStreamTime, Object message, String messageId) {
		super();
		this.queue = queue;
		this.worker = worker;
		this.boss = boss;
		this.consumerStartTime = consumerStartTime;
		this.upStreamTime = upStreamTime;
		this.message = message;
		this.messageId = messageId;
	}

	public ChannelMessageWrapper() {
		super();
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChannelMessageWrapper [messageId=" + messageId + ", queue="
				+ queue + ", worker=" + worker + ", workerName=" + workerName + ", boss=" + boss
				+ ", consumerStartTime=" + consumerStartTime
				+ ", upStreamTime=" + upStreamTime + "]";
	}


    public String getMessageId() {
		return messageId;
	}

	public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
}
