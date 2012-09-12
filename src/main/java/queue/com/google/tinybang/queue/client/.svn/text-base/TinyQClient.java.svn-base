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
package com.google.tinybang.queue.client;

import com.google.tinybang.queue.common.TinyQService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.nio.ByteBuffer;

/**
 * A common <tt>Thrift</tt> client for the put the data to the queue, and load the data from the queue.
 * <p> Will implement the connection pool for the further usage.
 *
 * Created by TinyBang
 * User: wenzhong
 * Date: 2/27/11
 * Time: 3:30 PM
 */
public class TinyQClient {

    private static final int PORT = 8777;

    public static void main(String[] args) {
        TTransport transport = new TSocket("localhost", PORT);
        TProtocol protocol = new TBinaryProtocol(transport);
        TinyQService.Client client = new TinyQService.Client(protocol);
        try {
            transport.open();
            client.put("test", ByteBuffer.wrap("Hello World".getBytes()));
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            transport.close();
        }
    }



}
