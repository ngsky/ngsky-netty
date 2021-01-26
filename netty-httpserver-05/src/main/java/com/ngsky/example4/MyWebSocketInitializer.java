package com.ngsky.example4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyWebSocketInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // http 协议编解码处理器
        pipeline.addLast(new HttpServerCodec());
        // 大数据流处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //
        pipeline.addLast(new HttpObjectAggregator(8192));
        // websocket 协议处理器
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new MyWebSocketHandler());
    }
}
