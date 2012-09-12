package com.tinybang.commonj;

import java.io.Serializable;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jun 25, 2009
 * Time: 9:10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Work extends Serializable {

    public long getIdentifier();

    public WorkStatus getStatus();

}