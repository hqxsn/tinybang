/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.event;

import com.google.tinybang.queue.channel.spi.ExceptionListener;

/**
 * A header of all the channel events.
 * <p> Wrapper of some common staff of the events for the issue tracking, retry and exception handling.
 * <p> It is a bit complex for most of the cases, you can define whether use it in the CodeC, for most of case (if you consider the performance a lot), do not use it.
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Oct 30, 2010
 */
public class EventHeader {

	private int retryTimes;
	
	private ExceptionListener exceptionListener;
	
	private String routeHistory;
	
	private Exception exception;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
	 * @return the retryTimes
	 */
	public int getRetryTimes() {
		return retryTimes;
	}

	/**
	 * @param retryTimes the retryTimes to set
	 */
	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	/**
	 * @return the exceptionListener
	 */
	public ExceptionListener getExceptionListener() {
		return exceptionListener;
	}

	/**
	 * @param exceptionListener the exceptionListener to set
	 */
	public void setExceptionListener(ExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
	}

	/**
	 * @return the routeHistory
	 */
	public String getRouteHistory() {
		return routeHistory;
	}

	/**
	 * @param routeHistory the routeHistory to set
	 */
	public void setRouteHistory(String routeHistory) {
		this.routeHistory = routeHistory;
	}
	
	public void appendRoute(String route) {
		if (this.routeHistory == null) {
			this.routeHistory = "START";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(this.routeHistory).append(" -> ").append(route);
		this.routeHistory = sb.toString();
	}
	
	public void updateRetryTimes() {
		this.retryTimes = this.retryTimes + 1;
	}
	
}
