package com.tinybang.commonj;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 24, 2010
 * Time: 1:27:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Router<T, V> {

    public T route(V value);

    enum RouterPolicy {
        CONSISTENCE_HASHING
    }

}
