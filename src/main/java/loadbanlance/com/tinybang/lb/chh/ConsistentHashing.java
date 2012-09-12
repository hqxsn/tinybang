package com.tinybang.lb.chh;


import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jul 23, 2010
 * Time: 12:02:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsistentHashing<T> {

    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();

    public ConsistentHashing(HashFunction hashFunction, int numberOfReplicas,
                          Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public static void main(String[] args) {
        Collection<String> serverAddress = new ArrayList<String>();
        serverAddress.add("10.201.10.45");
        serverAddress.add("10.201.10.46");
        serverAddress.add("10.201.10.47");
        serverAddress.add("10.201.10.48");
        ConsistentHashing<String> hashing = new ConsistentHashing<String>(new MD5HashFunction(),  4,
                serverAddress);
        long nano = System.nanoTime();
        UUID uuid1 = UUID.randomUUID();
        System.out.println(hashing.get(uuid1.toString()));
        UUID uuid2 = UUID.randomUUID();
        System.out.println(hashing.get(uuid2.toString()));
        UUID uuid3 = UUID.randomUUID();
        System.out.println(hashing.get(uuid3.toString()));
        UUID uuid4 = UUID.randomUUID();
        System.out.println(hashing.get(uuid4.toString()));
        UUID uuid5 = UUID.randomUUID();
        System.out.println(hashing.get(uuid5.toString()));
        UUID uuid6 = UUID.randomUUID();
        System.out.println(hashing.get(uuid6.toString()));
        UUID uuid7 = UUID.randomUUID();
        System.out.println(hashing.get(uuid7.toString()));
        UUID uuid8 = UUID.randomUUID();
        System.out.println(hashing.get(uuid8.toString()));

        System.out.println(hashing.get(uuid1.toString()));
        System.out.println(System.nanoTime() - nano);
        /*System.out.println(" remove node 10.201.10.47");
        serverAddress.remove("10.201.10.47");
        hashing = new ConsistentHashing<String>(new CRC32HashFunction(),  4,
                serverAddress);
        93733701
        97055911
        96647758

        95600978
        95046717

        System.out.println(hashing.get("SendRfpProcess"));
        System.out.println(hashing.get("SendRfpProcess"));
        System.out.println(hashing.get("SendRfpProcess"));
        System.out.println(hashing.get("SendResponseProcess"));
        System.out.println(hashing.get("SendResponseProcess"));
        System.out.println(hashing.get("PlannerContract"));
        System.out.println(hashing.get("SendResponseProcess"));
        System.out.println(hashing.get("PlannerContract"));*/
    }


}

