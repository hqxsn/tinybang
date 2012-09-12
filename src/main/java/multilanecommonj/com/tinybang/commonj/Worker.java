package com.tinybang.commonj;


/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 24, 2010
 * Time: 11:27:05 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Worker<P extends WorkEntry<? extends Work>> extends Runnable {

    public void start();

    public void stop();

    public Worker<P> getNextWorker();

    public Worker<P> getPreviousWorker();

    public int getWorkingNumbers();

    public String getId();

    public long getScheduledWorkingNumbers();

}
