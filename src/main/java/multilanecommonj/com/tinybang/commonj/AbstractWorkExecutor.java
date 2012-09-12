/**
 * Copyright 2010 TinyBang Inc. All rights reserved.
 */
package com.tinybang.commonj;


/**
 * An abstract class for the work executor to define the template of the executor and the constructor.
 * <p> Force you must use the constructor with {@link Work} to transfer the worker data.
 *
 * @author <a href="mailto:vincent.jin@TinyBang.com">vincent.jin</a>
 * Jun 1, 2010
 */
public abstract class AbstractWorkExecutor<T extends Work> implements WorkExecutor<T> {

	private EventListener<T> eventListener;

	protected T work;

	protected WorkEvent<T> workEvent;

	public AbstractWorkExecutor(T work) {
		this.work = work;
		this.workEvent = new WorkEvent<T>();
		this.workEvent.setEntryInfo(work);
	}

	@Override
	public abstract void execute(EventListener<T> listener);

	@Override
	public void run() {
		execute(this.getEventListener());
	}

	public abstract EventListener<T> getEventListener();
}