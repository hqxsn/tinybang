/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.spi;

import java.util.List;

import com.google.tinybang.queue.channel.config.Mode;


/**
 * Currently, it only support jms channel, so the configuration is simple.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jun 17, 2010
 */
public interface ChannelConfig {
	
	public int getDefaultRetryTimes();

	public String getQueue();
	
	public void setQueue(String queue);
	
	public void setConsumerCount(int count);
	
	public int getConsumerCount();
	
	public int getWorkerCount();
	
	public void setWorkerCount(int count);

	public Mode getMode();
	
	public void setMode(Mode mode);
	
	public List<Peer> getHandlers();
	
	public void setHandlers(List<Peer> handlers);
	
	public boolean isAync();
	
	public boolean isBatch();
	
	public int getBatchSize();
	
	public int getRetryTimes();
	
	public String getUndertaker();
	
	public class Peer {
		
		String handlerName;
		
		Type type = Type.UPSTREAM;

		public String getHandlerName() {
			return handlerName;
		}

		public void setHandlerName(String handlerName) {
			this.handlerName = handlerName;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}
		
		
		
	}
	/**
	 * Three types of the type.
	 * */
	public enum Type {
		UPSTREAM, DOWNSTREAM, LIMBO;
	}


}