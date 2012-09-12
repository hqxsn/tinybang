package com.tinybang.commonj;



/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 24, 2009
 * Time: 5:10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Pipe<T> {

	Queue<T> getReadyWorkQueue();

	void clear();

    public String getId();

	public static interface Factory<T> {
		Pipe<T> create();
        Pipe<T> create(String id);
	}
}