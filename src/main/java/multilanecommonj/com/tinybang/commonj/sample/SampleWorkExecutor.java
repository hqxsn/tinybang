package com.tinybang.commonj.sample;


import com.tinybang.commonj.EventListener;
import com.tinybang.commonj.WorkEvent;
import com.tinybang.commonj.WorkExecutor;
import com.tinybang.commonj.WorkStatus;

import java.util.concurrent.TimeUnit;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 25, 2010
 * Time: 12:50:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleWorkExecutor implements WorkExecutor {

    SampleWork sampleWork;
    Object object = new Object();

    public SampleWorkExecutor(SampleWork sampleWork) {
        this.sampleWork = sampleWork;
    }

    @Override
    public void execute(EventListener eventListener) {
        //System.out.println("do job successfully");

        sampleWork.getMap().get("KEY");
        //eventListener.triggerEvent(null);
        getResult();

        WorkEvent event = new WorkEvent();
        event.setStatus(WorkStatus.END.name());

        eventListener.triggerEvent(event);
    }

    public String getResult() {
        synchronized (object) {
            try {
                TimeUnit.MILLISECONDS.timedWait(object, 200l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "executed";
    }

    @Override
    public void run() {
        execute(SampleBenchmarkTester.getInstance());
    }
}
