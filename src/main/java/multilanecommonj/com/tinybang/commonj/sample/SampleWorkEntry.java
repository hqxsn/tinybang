package com.tinybang.commonj.sample;


import com.tinybang.commonj.*;


import java.util.Date;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 25, 2010
 * Time: 12:48:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleWorkEntry extends AbstractWorkEntry {

    private SampleWork work;

    private long readyQueueDate;

    @Override
    public WorkType getWorkType() {
        return WorkType.COMMAND;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Work getWork() {
        return new SampleWork();
    }

    @Override
    public WorkExecutor getWorkExecutor() {
        return new SampleWorkExecutor(new SampleWork());  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public WorkStatus getStatus() {
        return WorkStatus.END;  
    }

    public void setBeginThreadTime(Date date) {
        System.out.println(this + "--Enter Ready Queue:" + readyQueueDate + " Begin Thread:" + date);
    }

    public void setWork(SampleWork work) {
        this.work = work;
    }
}
