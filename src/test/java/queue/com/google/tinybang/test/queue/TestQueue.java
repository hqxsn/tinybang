package com.google.tinybang.test.queue;

import com.google.tinybang.Utils;
import com.google.tinybang.queue.channel.Channels;
import com.google.tinybang.queue.channel.spi.HandlerException;
import org.junit.Test;

/**
 * Created by TinyBang
 * User: wenzhong
 * Date: 2/26/11
 * Time: 6:12 PM
 */
public class TestQueue {

    @Test
    public void test() {
        try {
            Channels.write("test", "hello world");
        } catch (HandlerException e) {
            System.out.println(Utils.outputStackTrace(e));
        }
    }

}
