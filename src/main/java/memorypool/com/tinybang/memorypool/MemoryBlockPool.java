package com.tinybang.memorypool;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: 1/6/11
 * Time: 11:51 AM
 * This class provided the features with memory pool functions. And the
 * underlying mechanism based on java.nio.ByteBuffer
 */


public class MemoryBlockPool {

    static Map<Integer, LinkedBlockingQueue<MemoryBlock>> memoryPools;

    static {
        memoryPools = new ConcurrentHashMap<Integer, LinkedBlockingQueue<MemoryBlock>>();

    }

    long totalAllocate;

    public MemoryBlockPool() {
        init();
    }

    public void init() {
        int minSize = getMemoryPoolSegmentMinSize();
        int maxSize = getMemoryPoolSegmentMaxSize();

        int segmentSize = getMemoryPoolSegmentCounts();

        int segmentSizeScale = getMemoryPoolSegmentSizeScale();
        boolean offJvmHeap = isOffJVMHeap();

        int poolIncPace = getMemoryPoolSegmentSizeIncPace();


        for (int i = minSize; (i *= poolIncPace) <= maxSize;) {
            LinkedBlockingQueue<MemoryBlock> memorySegments = new LinkedBlockingQueue<MemoryBlock>(segmentSize);

            int poolSizeKey = i * segmentSizeScale;
            for (int j = 0; j < segmentSize; ++j) {
                memorySegments.offer(new MemoryBlockImpl(poolSizeKey, offJvmHeap));
                totalAllocate += i * segmentSizeScale;
            }

            memoryPools.put(poolSizeKey, memorySegments);
        }
    }

    public static int getMemoryPoolSegmentMinSize() {
        Integer size = Integer.getInteger("memory.pool.segment.min.size");
        return size == null ? 2 : size;
    }

    public static int getMemoryPoolSegmentMaxSize() {
        Integer size = Integer.getInteger("memory.pool.segment.max.size");
        return size == null ? 32 : size;
    }

    public static int getMemoryPoolSegmentSizeScale() {
        Integer scale = Integer.getInteger("memory.pool.segment.size.scale");
        return scale == null ? 1024 : scale;
    }

    public static int getMemoryPoolSegmentSizeIncPace() {
        Integer pace = Integer.getInteger("memory.pool.segment.size.inc.pace");
        return pace == null ? 2 : pace;
    }

    public static int getMemoryPoolSegmentCounts() {
        Integer cnt = Integer.getInteger("memory.pool.each.segment.counts");
        return cnt == null ? 50 : cnt;
    }

    public static boolean isOffJVMHeap() {
        return Boolean.getBoolean("memory.pool.allocate.offjvmheap");
    }

    public long totalAllocate() {
        return totalAllocate;
    }

    public MemoryBlock allocateByKey(int poolKey) {
        BlockingQueue<MemoryBlock> segments = memoryPools.get(poolKey * getMemoryPoolSegmentSizeScale());
        if (segments == null) {
            throw new IllegalArgumentException("Cannot allocate the memory according to key " + poolKey + " the key only could be " + getMemoryPoolSegmentMinSize()
                    + " or " + getMemoryPoolSegmentMinSize() + " factorial with " + getMemoryPoolSegmentSizeScale() + " and the maximum is " + getMemoryPoolSegmentMaxSize());
        }
        return segments.poll();
    }

    public MemoryBlock allocateBySize(int size) {
        int minSize = getMemoryPoolSegmentMinSize();
        int maxSize = getMemoryPoolSegmentMaxSize();
        int segmentSizeScale = getMemoryPoolSegmentSizeScale();

        int poolKey = 0;
        for (int i = minSize; (i *= 2) <= maxSize;) {
            poolKey = i;
            int tempSize = poolKey * segmentSizeScale;
            if (size <= tempSize) {
                break;
            }
        }

        return allocateByKey(poolKey);
    }

    public MemoryBlock allocateByBytes(byte[] bytes) {
        MemoryBlock memoryBlock = allocateBySize(bytes.length);

        memoryBlock.putMemoryBlockWithGivenByteArray(bytes);
        return memoryBlock;
    }

    public void release(MemoryBlock block) {
        BlockingQueue<MemoryBlock> segments = memoryPools.get(block.sizeOf());
        if (segments == null) {
            throw new IllegalArgumentException("Cannot release the memory according to key " + block.sizeOf() + " the key only could be " + getMemoryPoolSegmentMinSize()
                    + " or " + getMemoryPoolSegmentMinSize() + " factorial with " + getMemoryPoolSegmentSizeScale() + " and the maximum is " + getMemoryPoolSegmentMaxSize());
        }

        segments.offer(block);
    }

    public static void main(String[] args) {
        MemoryBlockPool pool = new MemoryBlockPool();
        pool.init();
        System.out.println("Total allocate:" + pool.totalAllocate());
        MemoryBlock block = pool.allocateByKey(4);
        pool.release(block);
        pool.destroy();
    }

    public void destroy() {
        Set<Integer> keySet = memoryPools.keySet();
        for (int key : keySet) {
            LinkedBlockingQueue<MemoryBlock> segments = memoryPools.remove(key);
            segments.clear();
        }
    }
}

