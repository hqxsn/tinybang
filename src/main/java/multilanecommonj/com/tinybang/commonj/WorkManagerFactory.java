package com.tinybang.commonj;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Andy.Song
 * Created: Jun 29, 2009
 */
public class WorkManagerFactory {

    private static final ConcurrentMap<WorkType, WorkManager> _work_manager_map = new ConcurrentHashMap<WorkType, WorkManager>();

	private static WorkManagerFactory _work_manager_factory = new WorkManagerFactory();

	private WorkManagerFactory() {
		init();
	}

	public ConcurrentMap<WorkType, WorkManager> getWorkManagerMap() {
		return _work_manager_map;
	}


    public WorkManager getWorkManager(WorkType workType) {
        WorkManager manager = _work_manager_map.get(workType);
        if(manager == null) {
            synchronized(_work_manager_map) {
                _work_manager_map.put(workType, new WorkManager(workType));
            }
        } else {
            return manager;
        }
        return _work_manager_map.get(workType);
    }

	/**
	 *
	 */
	private void init() {

	}

	public static WorkManagerFactory getInstance() {
		return _work_manager_factory;
	}

	/**
	 * Only start one time during the init.
	 * @param workType
	 */
	public void start(WorkType workType) {

	}

	public void stop(WorkType workType) {

	}


}