package com.tinybang.lb.chh;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jul 23, 2010
 * Time: 12:05:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HashFunction {

    public Long hash(String s);

    public Long hash(Object key);
}
