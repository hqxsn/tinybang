package com.tinybang.commonj;


/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 24, 2009
 * Time: 6:10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EventListener<T extends Work>{

    public static final int WORK_ACCEPTED = 1;
    public static final int WORK_REJECTED = 2;

    public void triggerEvent(WorkEvent<T> event);

}