package com.tinybang.commonj.sample;



import com.tinybang.commonj.EventListener;
import com.tinybang.commonj.WorkEvent;
import com.tinybang.commonj.WorkManagerFactory;
import com.tinybang.commonj.WorkStatus;
import com.tinybang.commonj.WorkType;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 25, 2010
 * Time: 12:55:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleBenchmarkTester implements EventListener {

    private AtomicInteger integer = new AtomicInteger(1000000);

    private static SampleBenchmarkTester instance = new SampleBenchmarkTester();

    private SampleBenchmarkTester() {

    }




    public static void main(String[] args) throws InterruptedException {
        WorkManagerFactory.getInstance().start(WorkType.COMMAND);


        System.out.println(System.nanoTime());

        for (int i = 0; i < 1000000; ++i) {
            SampleWorkEntry entry = new SampleWorkEntry();
            SampleWork work = new SampleWork();
            entry.setWork(work);
            WorkManagerFactory.getInstance().getWorkManager(WorkType.COMMAND).schedule(UUID.randomUUID(), entry);
        }


        /*Thread tr = new Thread() {

            public void run() {
                while(true)
                try {
                    SampleWorkEntry entry = new SampleWorkEntry();
        SampleWork work = new SampleWork();
        entry.setWork(work);
                    WorkManagerFactory.getInstance().getWorkManager(WorkType.COMMAND).schedule(UUID.randomUUID(), entry);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread tr1 = new Thread() {

            public void run() {
                while(true)
                try {
                    SampleWorkEntry entry = new SampleWorkEntry();
        SampleWork work = new SampleWork();
        entry.setWork(work);
                    WorkManagerFactory.getInstance().getWorkManager(WorkType.COMMAND).schedule(UUID.randomUUID(), entry);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread tr3 = new Thread() {

            public void run() {

                while(true) {
                try {
                    SampleWorkEntry entry = new SampleWorkEntry();
        SampleWork work = new SampleWork();
        entry.setWork(work);
                    WorkManagerFactory.getInstance().getWorkManager(WorkType.COMMAND).schedule(UUID.randomUUID(), entry);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            }
        };

        Thread tr4 = new Thread() {

            public void run() {
                while(true)
                try {
                    SampleWorkEntry entry = new SampleWorkEntry();
        SampleWork work = new SampleWork();
        entry.setWork(work);
                    WorkManagerFactory.getInstance().getWorkManager(WorkType.COMMAND).schedule(UUID.randomUUID(), entry);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        tr1.start();
        tr3.start();
        tr4.start();
        tr.start();*/

        //System.out.println(WorkManagerFactory.getInstance().getWorkManager(WorkType.COMMAND).getStatisticsInfo().toString());

        System.out.println(System.nanoTime());
        System.out.println(WorkManagerFactory.getInstance().getWorkManager(WorkType.COMMAND).getStatisticsInfo().toString());

        //WorkManagerFactory.getInstance().stop(WorkType.COMMAND);
    }


    @Override
    public void triggerEvent(WorkEvent workEvent) {
        if (workEvent.getStatus().equals(WorkStatus.END.name())) {
            int left = integer.decrementAndGet();
            if(left == 0) {
                System.out.println(System.nanoTime());
            }
        }
    }

    public static EventListener getInstance() {
        return instance;
    }
}
