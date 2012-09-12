package com.tinybang.memorypool;

import java.nio.ByteBuffer;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: 1/4/11
 * Time: 1:59 PM
 * Implement the MemoryBlock interface.
 *   two types of memory allocation modes:
 *       ** Heap memory                     ----          JVM Heap
 *       ** Direct memory (native memory)   ----          Off JVM Heap
 */
public class MemoryBlockImpl implements MemoryBlock {

    ByteBuffer buffer;

    int blockSize;

    boolean offJVMHeap;

    MemoryBlockImpl(int blockSize, boolean offJVMHeap) {
        this.blockSize = blockSize;
        this.offJVMHeap = offJVMHeap;
        allocate();
    }

    void allocate() {
        if (offJVMHeap) {
            buffer = ByteBuffer.allocateDirect(blockSize);
        } else {
            buffer = ByteBuffer.allocate(blockSize);
        }
    }

    @Override
    public int sizeOf() {
        return blockSize;
    }

    @Override
    public ByteBuffer getMemoryBlockAsByteBuffer() {
        return buffer;
    }

    @Override
    public byte[] getMemoryBlockAllocatedAsByteArray() {
        return buffer.array();
    }

    @Override
    public byte[] getMemoryBlockUsedAsByteArray() {
        int length = buffer.position();
        byte[] byteArray = new byte[length];
        if (!isOffJVMHeap()) {

            System.arraycopy(buffer.array(), 0, byteArray, 0,
                    byteArray.length);
        } else {
            buffer.get(byteArray, 0, length);
        }

        return byteArray;
    }

    @Override
    public void putMemoryBlockWithGivenByteArray(byte[] byteArray) {
        ByteBuffer byteBuffer = getMemoryBlockAsByteBuffer();
    	byteBuffer.put(byteArray);
    	byteBuffer.rewind();
    	byteBuffer.limit(byteArray.length);
    }

    @Override
    public boolean isOffJVMHeap() {
        return offJVMHeap;
    }

    @Override
    public void reset() {
        buffer.clear();
    }
}

