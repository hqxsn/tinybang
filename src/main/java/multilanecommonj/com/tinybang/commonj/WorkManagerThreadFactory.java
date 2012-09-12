package com.tinybang.commonj;


import java.util.UUID;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 24, 2010
 * Time: 1:07:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkManagerThreadFactory implements ThreadFactory {
    static final AtomicInteger poolNumber = new AtomicInteger(1);
    final ThreadGroup group;
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;

    public WorkManagerThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "workMgr-pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    public Thread newThread(Runnable r) {
        WorkManagerThread t = new WorkManagerThread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);

        t.setUncaughtExceptionHandler(WorkManagerUncaughtExceptionHandler.getInstance());
        return t;
    }
}

class WorkManagerThread extends Thread {

    Runnable r = null;

    public WorkManagerThread(ThreadGroup group, Runnable r, String name, long stackSize) {
        super(group, r, name, stackSize);
        this.r = r;
    }

    public String toString() {
        return r.toString();
    }

}

class WorkManagerUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final WorkManagerUncaughtExceptionHandler instance = new WorkManagerUncaughtExceptionHandler();


//    private static final StarCiteLogService logger = new StarCiteLogService();

    private WorkManagerUncaughtExceptionHandler() {

    }

    public static WorkManagerUncaughtExceptionHandler getInstance() {
        return instance;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        /*logger.log(trapLogBuilder.build(TrapCode.WORK_MANAGER_CURRENT_WORKER_COMMON_ERROR,
                        UUID.randomUUID().toString(), "ThreadPoolExecutor_executor", "", "", Utils.outputStackTrace(e),
                        t.toString()));*/
    }
}
