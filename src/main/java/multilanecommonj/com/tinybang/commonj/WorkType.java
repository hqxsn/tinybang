package com.tinybang.commonj;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 24, 2009
 * Time: 6:58:17 PM
 * To change this template use File | Settings | File Templates.
 */
public enum WorkType {

    COMMAND {
        public int getPipeQueueSize() {
            return Integer.getInteger("de.command.workmanager.pipe.queue.size", 100);
        }

        public int getPipeQueueNumbers() {
            return Integer.getInteger("de.command.workmanager.pipe.queue.numbers", 4);
        }

        public int getWorkerThreadCount() {
            return Integer.getInteger("de.command.work.manager.thread.count", 15);
        }

        public int getWorkerMaxThreadCount() {
            return Integer.getInteger("de.command.work.manager.max.thread.count", 20);
        }

        public long getWorkerThreadKeepAliveInMilliseconds() {
            return Integer.getInteger("de.command.work.manager.keep.live.msecs", 0);
        }

        public int getWorkerPipeRatio() {
            return Integer.getInteger("de.command.work.manager.worker.pipe.ratio", 2);
        }


    }, LOG {
        public int getPipeQueueSize() {
            return Integer.getInteger("de.log.workmanager.pipe.queue.size");
        }

        public int getPipeQueueNumbers() {
            return Integer.getInteger("de.log.workmanager.pipe.queue.numbers");
        }

        public int getWorkerThreadCount() {
            return Integer.getInteger("de.log.work.manager.thread.count");
        }

        public int getWorkerMaxThreadCount() {
            return Integer.getInteger("de.log.work.manager.max.thread.count");
        }

        public long getWorkerThreadKeepAliveInMilliseconds() {
            return Integer.getInteger("de.log.work.manager.keep.live.msecs");
        }

        public int getWorkerPipeRatio() {
            return Integer.getInteger("de.log.work.manager.worker.pipe.ratio");
        }
    }, LOG_ANALYSIS {
        public int getPipeQueueSize() {
            return Integer.getInteger("de.log.analysis.workmanager.pipe.queue.size");
        }

        public int getPipeQueueNumbers() {
            return Integer.getInteger("de.log.analysis.workmanager.pipe.queue.numbers");
        }

        public int getWorkerThreadCount() {
            return Integer.getInteger("de.log.analysis.work.manager.thread.count");
        }

        public int getWorkerMaxThreadCount() {
            return Integer.getInteger("de.log.analysis.work.manager.max.thread.count");
        }

        public long getWorkerThreadKeepAliveInMilliseconds() {
            return Integer.getInteger("de.log.analysis.work.manager.keep.live.msecs");
        }

        public int getWorkerPipeRatio() {
            return Integer.getInteger("de.log.analysis.work.manager.worker.pipe.ratio");
        }
    };

    public int getPipeQueueSize() {
        throw new AbstractMethodError();
    }

    public int getPipeQueueNumbers() {
        throw new AbstractMethodError();
    }

    public int getWorkerThreadCount() {
        throw new AbstractMethodError();
    }

    public int getWorkerMaxThreadCount() {
        throw new AbstractMethodError();
    }

    public long getWorkerThreadKeepAliveInMilliseconds() {
        throw new AbstractMethodError();
    }

    public int getWorkerPipeRatio() {
        throw new AbstractMethodError();
    }

}