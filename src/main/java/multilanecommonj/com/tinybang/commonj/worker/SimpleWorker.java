package com.tinybang.commonj.worker;



import com.tinybang.commonj.Queue;
import com.tinybang.commonj.Recycle;
import com.tinybang.commonj.Work;
import com.tinybang.commonj.WorkEntry;
import com.tinybang.commonj.WorkManagerFactory;
import com.tinybang.commonj.WorkManagerThreadFactory;
import com.tinybang.commonj.WorkType;
import com.tinybang.commonj.Worker;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 24, 2010
 * Time: 11:32:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleWorker<P extends WorkEntry<? extends Work>> implements Worker, Recycle<SimpleWorker<P>> {

    private Queue<P> readyQueue;

    private static AtomicBoolean isRunning = new AtomicBoolean(true);

    private Worker<P> nextWorker;

    private Worker<P> previousWorker;

    private ThreadPoolExecutor m_threadPool;

    private static AtomicBoolean needRecycle = new AtomicBoolean(false);

    private WorkType workType = null;

    private String id;

    public SimpleWorker(Queue<P> queue, WorkType workType, String workerId) {
        this.readyQueue = queue;
        m_threadPool = new ThreadPoolExecutor(workType.getWorkerThreadCount(), workType.getWorkerMaxThreadCount(),
                workType.getWorkerThreadKeepAliveInMilliseconds(), TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new WorkManagerThreadFactory());
        this.workType = workType;

        id = queue.getId() + " | " + workerId;
    }

    SimpleWorker(Queue<P> readyQueue, WorkType workType, Worker<P> nextWorker, Worker<P> previousWorker) {
        this.readyQueue = readyQueue;
        m_threadPool = new ThreadPoolExecutor(workType.getWorkerThreadCount(), workType.getWorkerMaxThreadCount(),
                workType.getWorkerThreadKeepAliveInMilliseconds(), TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new WorkManagerThreadFactory());
        this.nextWorker = nextWorker;
        this.previousWorker = previousWorker;
        this.workType = workType;
        ((SimpleWorker<P>) this.previousWorker).setNextWorker(this);
    }

    @Override
    public void start() {
        while (isRunning.get()) {
            try {

                P entry = readyQueue.take();
                entry.setOutOfReadyQueueTime(System.currentTimeMillis());

                if (entry != null) {
                    if (entry.getWorkExecutor() != null) {
                        try {
                            entry.setWorkExecutionBeginTime(System.currentTimeMillis());
                            m_threadPool.execute(entry.getWorkExecutor());
                            entry.setBeginThreadTime(System.currentTimeMillis());
                        } catch (RejectedExecutionException rejectException) {
                            //Current thread pool was busy, so let's re-enter into the queue.
                            rejectException.printStackTrace();


                            if (readyQueue.remainingCapacity() <= 5) {
                                WorkManagerFactory.getInstance().getWorkManager(workType).schedule(UUID.randomUUID(), entry);
                            } else {
                                readyQueue.put(entry);
                            }
                        } finally {


                        }
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        setRunning(false);
        m_threadPool.shutdown();

    }

    @Override
    public Worker<P> getNextWorker() {
        return nextWorker;
    }

    @Override
    public Worker<P> getPreviousWorker() {
        return previousWorker;
    }

    @Override
    public int getWorkingNumbers() {
        return m_threadPool.getActiveCount();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getScheduledWorkingNumbers() {
        return m_threadPool.getTaskCount();
    }

    public static void setRunning(boolean running) {
        isRunning.compareAndSet(true, running);
    }

    @Override
    public void run() {
        start();
    }

    public void setNextWorker(Worker<P> nextWorker) {
        this.nextWorker = nextWorker;
    }

    public void setPreviousWorker(Worker<P> previousWorker) {
        this.previousWorker = previousWorker;
    }

    @Override
    public SimpleWorker<P> newInstance() {
        stop();
        SimpleWorker<P> worker = new SimpleWorker<P>(readyQueue, workType, nextWorker, previousWorker);

        WorkManagerFactory.getInstance().getWorkManager(workType).newRecycleWorker(worker);

        return worker;
    }
}
