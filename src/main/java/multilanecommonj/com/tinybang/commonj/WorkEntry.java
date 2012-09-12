package com.tinybang.commonj;


import java.util.Date;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 24, 2009
 * Time: 6:01:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WorkEntry<T extends Work> {

    public WorkType getWorkType();

    public T getWork();

    public WorkExecutor<T> getWorkExecutor();

    public void setEnterReadyQueueTime(long enterReadyQueueTime);

    public long getEnterReadyQueueTime();

    public void setEnterPendingQueueTime(long enterPendingQueueTime);

    public long getEnterPendingQueueTime();

    public void setOutOfReadyQueueTime(long outOfReadyQueueTime);

    public long getOutOfReadyQueueTime();

    public void setWorkExecutionBeginTime(long workExecutionBeginTime);

    public long getWorkExecutionBeginTime();

    public WorkStatus getStatus();

    public void setBeginThreadTime(long date);

    public long getBeginThreadTime();

    public static interface Factory<T> {
        WorkEntry<?> create();
	}

}