package com.tinybang.commonj.router;



import com.tinybang.commonj.Router;
import com.tinybang.lb.chh.ConsistentHashing;
import com.tinybang.lb.chh.MD5HashFunction;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 24, 2010
 * Time: 4:13:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class PipeIDConsistenceHashingRouter implements Router<Integer, UUID> {

    private ConsistentHashing<Integer> lbHashing;

    public PipeIDConsistenceHashingRouter(Collection<Integer> pipeIds) {
        lbHashing = new ConsistentHashing<Integer>(new MD5HashFunction(), pipeIds.size(), pipeIds);
    }

    @Override
    public Integer route(UUID value) {
        return lbHashing.get(value);
    }
}
