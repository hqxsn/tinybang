package com.tinybang.commonj;


import com.tinybang.commonj.pipe.PipeManager;
import com.tinybang.commonj.worker.SimpleWorker;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 24, 2009
 * Time: 11:19:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkManager implements Serializable {

    private static final long serialVersionUID = 6815418391078530104L;

    private PipeManager<WorkEntry<? extends Work>> pipeManager;
    private WorkType workType;

    private Worker root;

    private ExecutorService m_threadPool;

    private boolean isInitialization = false;

    public WorkManager(WorkType workType) {

        pipeManager = new PipeManager<WorkEntry<? extends Work>>();
        pipeManager.init(workType);
        this.workType = workType;

        List<Pipe<WorkEntry<? extends Work>>> pipes = pipeManager.getAvailablePipes(workType);

        m_threadPool = Executors.newFixedThreadPool(pipes.size() * workType.getWorkerPipeRatio());

        Worker worker = null;
        for (Pipe<WorkEntry<? extends Work>> pipe : pipes) {
            for (int i = 0; i < workType.getWorkerPipeRatio(); ++i) {
                if (root == null) {
                    root = worker = new SimpleWorker<WorkEntry<? extends Work>>(pipe.getReadyWorkQueue(), workType, SimpleWorker.class.getSimpleName() + " {" + i + "}");
                    m_threadPool.execute(worker);
                } else {
                    Worker tmpWorker = worker;
                    worker = new SimpleWorker<WorkEntry<? extends Work>>(pipe.getReadyWorkQueue(), workType, SimpleWorker.class.getSimpleName() + " {" + i + "}");
                    assert ((SimpleWorker) tmpWorker) != null;
                    ((SimpleWorker) tmpWorker).setNextWorker(worker);
                    ((SimpleWorker) worker).setPreviousWorker(tmpWorker);

                    m_threadPool.execute(worker);
                }


            }
        }

        isInitialization = true;

        if (needProfiling()) {
            MonitorThread thread = new MonitorThread();
            thread.setDaemon(true);
            thread.start();
        }
    }

    public boolean needProfiling() {
        return Boolean.getBoolean("multilane.commonj.profiling.enable");
    }


    public PipeManager<WorkEntry<? extends Work>> getPipeManager() {
        return pipeManager;
    }

    public boolean schedule(UUID identifier, WorkEntry<? extends Work> entry) throws InterruptedException {

        pipeManager.scheduleToReadyQueue(workType, entry, identifier);
        entry.setEnterReadyQueueTime(System.currentTimeMillis());
        //System.out.println("put " + (System.nanoTime() - miss));
        return true;
    }

    public void waitForAll(WorkEntry<? extends Work>... entrys) {
        boolean isAllFinished = false;

        while (!isAllFinished) {

            for (WorkEntry<? extends Work> entry : entrys) {
                if (entry.getWork().getStatus() != WorkStatus.END) {
                    isAllFinished = false;
                    break;
                }
                isAllFinished = true;
            }
        }
    }

    public void shutdown() {
        Worker worker = root;
        do {
            worker.stop();
        } while ((worker = worker.getNextWorker()) != null);

        m_threadPool.shutdownNow();
    }

    public StringBuilder getStatisticsInfo() {
        StringBuilder builder = new StringBuilder();

        List<Pipe<WorkEntry<? extends Work>>> pipes = pipeManager.getAvailablePipes(workType);
        int size = pipes.size();
        builder.append(workType).append(':').append(size).append(" pipes").append(System.getProperty("line.separator"));
        for (int i = 0; i < size; ++i) {
            Pipe<WorkEntry<? extends Work>> pipe = pipes.get(i);
            builder.append(pipe.getId()).append(" with ").append(pipe.getReadyWorkQueue().size()).append(" number of entity");
            builder.append(System.getProperty("line.separator"));
        }

        Worker worker = root;
        int inx = 0;
        do {
            size = worker.getWorkingNumbers();
            builder.append(worker.getId()).append(" executing ").append(size).append(" number of works");
            builder.append(System.getProperty("line.separator"));
            builder.append(worker.getId()).append(" scheduled ").append(worker.getScheduledWorkingNumbers()).append(" number of works");
            builder.append(System.getProperty("line.separator"));
        } while ((worker = worker.getNextWorker()) != null);

        return builder;
    }

    public boolean isInitialization() {
        return isInitialization;
    }

    public void setInitialization(boolean initialization) {
        isInitialization = initialization;
    }

    public void newRecycleWorker(Worker worker) {
        m_threadPool.execute(worker);
    }


     class MonitorThread extends Thread {
        Object object = new Object();


        public void run() {

            while (true) {

                synchronized (object) {
                    try {
                        TimeUnit.MILLISECONDS.timedWait(object, 30000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}