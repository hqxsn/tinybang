package com.tinybang.commonj;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Represents a one-way communication channel. Abstract notion of putting
 * objects and receiving them (take or poll)
 *
 * @param <T>
 */
public interface Queue<T> {
    public static interface Factory<T> {
        public Queue<T> create();
        public Queue<T> create(String id);
    }

    T poll(long timeout, TimeUnit unit) throws InterruptedException;

    T put(T item) throws InterruptedException;

    T take() throws InterruptedException;

    int size();

    void clear();

    boolean put(T item, long time, TimeUnit timeUnit) throws InterruptedException;

    Iterator<T> iterator();

    int remainingCapacity();

    String getId();
}