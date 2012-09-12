package com.tinybang.commonj;



/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 24, 2009
 * Time: 6:09:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WorkExecutor<T extends Work> extends Runnable {

    public void execute(EventListener<T> listener);

}