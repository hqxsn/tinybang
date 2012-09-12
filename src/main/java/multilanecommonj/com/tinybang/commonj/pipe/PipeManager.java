package com.tinybang.commonj.pipe;





import com.tinybang.commonj.Pipe;
import com.tinybang.commonj.Router;
import com.tinybang.commonj.Work;
import com.tinybang.commonj.WorkEntry;
import com.tinybang.commonj.WorkType;
import com.tinybang.commonj.queue.BlockingQueueBasedQueue;
import com.tinybang.commonj.router.PipeIDConsistenceHashingRouter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 25, 2009
 * Time: 5:28:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class PipeManager<T extends WorkEntry<? extends Work>> {
    private final Map<WorkType, List<Pipe<T>>> m_pipes;
    private final Map<WorkType, Router<Integer, UUID>> routers;



    private Router.RouterPolicy policy;

//    private static transient Log log = null;

    public PipeManager() {
        m_pipes = new ConcurrentHashMap<WorkType, List<Pipe<T>>>();
        policy = Router.RouterPolicy.CONSISTENCE_HASHING;
        routers = new ConcurrentHashMap<WorkType, Router<Integer, UUID>>();
    }

    public void init(WorkType workType) {

        ExecutionPipe.Factory<T> factory =
                new ExecutionPipe.Factory<T>(new BlockingQueueBasedQueue.Factory<T>(workType.getPipeQueueSize()));

        List<Pipe<T>> pipeList = new ArrayList<Pipe<T>>(workType.getPipeQueueNumbers());
        Collection<Integer> pipeIds = new ArrayList<Integer>();
        for (int i = 0; i < workType.getPipeQueueNumbers(); ++i) {
            Pipe<T> pipe = factory.create(workType.name() + "_PIPE_" + "{" + i + "}");
            pipeList.add(pipe);
            pipeIds.add(i+1);
            
        }

        m_pipes.put(workType, pipeList);

        routers.put(workType, new PipeIDConsistenceHashingRouter(pipeIds));

    }

    public List<Pipe<T>> getAvailablePipes(WorkType workType) {
        return m_pipes.get(workType);
    }

    public Pipe<T> getOrCreatePipe(WorkType workType, UUID identifier) {
        assert workType != null;


        Router<Integer, UUID> router = routers.get(workType);

        if(router == null) {
            init(workType);
            router = routers.get(workType);
        }

        List<Pipe<T>> pipeList = m_pipes.get(workType);

        int inx = router.route(identifier);
        //TODO: add log information if the initialization has problem.
        return pipeList.get(inx - 1);
    }

    public void scheduleToReadyQueue(WorkType workType, T workEntry, UUID identifier) throws InterruptedException {
        getOrCreatePipe(workType, identifier).getReadyWorkQueue().put(workEntry);
    }

}