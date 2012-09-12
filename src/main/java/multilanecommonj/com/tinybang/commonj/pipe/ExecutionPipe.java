package com.tinybang.commonj.pipe;

import com.tinybang.commonj.Pipe;
import com.tinybang.commonj.Queue;


import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 24, 2009
 * Time: 5:51:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExecutionPipe<T> implements Pipe<T> {

    private final Queue<T> readyWorkQueue;

    private String id;

    public String getId() {
        return id;
    }

    public static class Factory<T> implements Pipe.Factory<T> {
		private final Queue.Factory<T> m_queueFactory;

		public Factory(final Queue.Factory<T> queueFactory) {
			m_queueFactory = queueFactory;
		}

		public Pipe<T> create() {
			return new ExecutionPipe<T>(m_queueFactory.create());
		}

        @Override
        public Pipe<T> create(String id) {
            return new ExecutionPipe<T>(m_queueFactory.create(id), ExecutionPipe.class.getSimpleName() + "_" + id);
        }
    }

    public ExecutionPipe(final Queue<T> readyWorkQueue) {
        this.readyWorkQueue = readyWorkQueue;
    }

    public ExecutionPipe(final Queue<T> readyWorkQueue, String id) {
        this.readyWorkQueue = readyWorkQueue;
        this.id = id;
    }


    public Queue<T> getReadyWorkQueue() {
        return readyWorkQueue;
    }

    public void clear() {
        readyWorkQueue.clear();
    }
}