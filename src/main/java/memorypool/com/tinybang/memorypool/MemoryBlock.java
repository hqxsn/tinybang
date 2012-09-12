package com.tinybang.memorypool;

import java.nio.ByteBuffer;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: 1/4/11
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MemoryBlock {

    public int sizeOf();

    public ByteBuffer getMemoryBlockAsByteBuffer();

    public byte[] getMemoryBlockAllocatedAsByteArray();

    public byte[] getMemoryBlockUsedAsByteArray();

    public void putMemoryBlockWithGivenByteArray(byte[] byteArray);

    public boolean isOffJVMHeap();

    public void reset();
}
