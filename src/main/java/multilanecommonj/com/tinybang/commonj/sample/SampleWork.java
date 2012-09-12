package com.tinybang.commonj.sample;


import com.tinybang.commonj.Work;
import com.tinybang.commonj.WorkStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Nov 25, 2010
 * Time: 12:49:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleWork implements Work {

    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("KEY", "12342");
        return map;
    }

    @Override
    public long getIdentifier() {
        return 0;
    }

    @Override
    public WorkStatus getStatus() {
        return WorkStatus.END;
    }
}
