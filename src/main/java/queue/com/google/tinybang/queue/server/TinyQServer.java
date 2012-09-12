/**  Copyright [2011] TinyBang

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.google.tinybang.queue.server;

import com.google.tinybang.queue.common.TinyQService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.nio.ByteBuffer;
import java.text.MessageFormat;

/**
 * Created by TinyBang
 * User: wenzhong
 * Date: 2/27/11
 * Time: 3:01 PM
 */
public class TinyQServer {

    private static final int PORT = 8777;

    public void start() {

        try {
            TServerSocket transport = new TServerSocket(PORT);
            TinyQService.Processor processor = new TinyQService.Processor(new TinyQHandler());
            TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory(true, true);
            TServer server = new TThreadPoolServer(processor, transport, factory);
            server.serve();
        } catch (TTransportException e) {
            throw new IllegalStateException(MessageFormat.format("Cannot start the server with the port {0}", PORT));
        }

    }

    public static void main(String[] args) {
        TinyQServer server = new TinyQServer();
        server.start();
    }

    private static class TinyQHandler implements TinyQService.Iface {

        @Override
        public void put(String queue, ByteBuffer data) throws TException {
            String s = new String(data.array());
            System.out.println("Queue " + queue + " data " + s);
        }

        @Override
        public ByteBuffer get(String queue) throws TException {
            return null;
        }
    }


}
