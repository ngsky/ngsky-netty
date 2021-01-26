package com.ngsky.example10;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new IntegerToByteEncoder());
//        pipeline.addLast(new ByteToIntegerDecoder());
        pipeline.addLast(new ByteToIntegerDecoder2());
        pipeline.addLast(new MyClientHandler());
    }
}
