package com.tinybang.commonj;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: 12/29/10
 * Time: 11:58 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractWorkEntry<T extends Work> implements WorkEntry<T> {
    long enterReadyQueueTime;
    long enterPendingQueueTime;
    long outOfReadyQueueTime;
    long workExecutionBeginTime;
    long beginThreadTime;

    public void setEnterReadyQueueTime(long enterReadyQueueTime) {
        this.enterReadyQueueTime = enterReadyQueueTime;
    }

    public long getEnterReadyQueueTime() {
        return enterReadyQueueTime;
    }

    public void setEnterPendingQueueTime(long enterPendingQueueTime) {
        this.enterPendingQueueTime = enterPendingQueueTime;
    }

    public long getEnterPendingQueueTime() {
        return enterPendingQueueTime;
    }

    public void setOutOfReadyQueueTime(long outOfReadyQueueTime) {
        this.outOfReadyQueueTime = outOfReadyQueueTime;
    }

    public long getOutOfReadyQueueTime() {
        return outOfReadyQueueTime;
    }

    public void setWorkExecutionBeginTime(long workExecutionBeginTime) {
        this.workExecutionBeginTime = workExecutionBeginTime;
    }

    public long getWorkExecutionBeginTime() {
        return workExecutionBeginTime;
    }

    public void setBeginThreadTime(long beginThreadTime) {
        this.beginThreadTime = beginThreadTime;
    }

    public long getBeginThreadTime() {
        return this.beginThreadTime;
    }
}
