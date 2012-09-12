package com.tinybang.commonj.queue;



import com.tinybang.commonj.Queue;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * An implementation of a Channel that uses a BlockingQueue. The default
 * constructor instantiates an unbounded LinkedBlockingQueue for the Queue.
 *
 * @param <T>
 * The type of message passed in the Channel
 */
public class BlockingQueueBasedQueue<T> implements Queue<T> {

    private String id;

    public BlockingQueueBasedQueue(int m_size, String id) {
        this(new LinkedBlockingQueue<T>(m_size), id);
    }

    public static class Factory<T> implements Queue.Factory<T> {
        private final int m_size;

        public Factory(final int size) {
            m_size = size;
        }

        public Queue<T> create() {
            return new BlockingQueueBasedQueue<T>(m_size);
        }

        @Override
        public Queue<T> create(String id) {
            return new BlockingQueueBasedQueue<T>(m_size, BlockingQueueBasedQueue.class.getSimpleName() + "_" + id);
        }
    }

    private final BlockingQueue<T> m_queue;

    BlockingQueueBasedQueue(int size) {
        this(new LinkedBlockingQueue<T>(size), UUID.randomUUID().toString());
    }

    BlockingQueueBasedQueue(final BlockingQueue<T> queue, String id) {
        m_queue = queue;
        this.id = id;
    }

    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        return m_queue.poll(timeout, unit);
    }

    public T put(T item) throws InterruptedException {
        m_queue.put(item);
        return item;
    }

    public boolean put(T item, long time, TimeUnit timeUnit) throws InterruptedException {
        return m_queue.offer(item, time, timeUnit);
    }

    public T take() throws InterruptedException {
        return m_queue.take();
    }

    public int size() {
        return m_queue.size();
    }

    public void clear() {
        m_queue.clear();
    }

    public Iterator<T> iterator() {
        return m_queue.iterator();
    }

    public int remainingCapacity() {
        return m_queue.remainingCapacity();
    }

    @Override
    public String getId() {
        return id;
    }
}