package com.java.bufferd.io;

import io.netty.buffer.ByteBuf;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class Test {
    public static void main(String[] args) {
        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);
        ByteBuffer[] message = new ByteBuffer[]{header, body};


    }
}
