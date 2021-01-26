package com.ngsky.example5;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyNettyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 解码和编码，应和客户端一致
//       ph.addLast(new FixedLengthFrameDecoder(100));   //定长数据帧的解码器 ，每帧数据100个字节就切分一次。  用于解决粘包问题
//         ph.addLast(new LineBasedFrameDecoder(2048));     //字节解码器 ,其中2048是规定一行数据最大的字节数。  用于解决拆包问题

//        pipeline.addLast("aggregator", new HttpObjectAggregator(4096));
        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast("handler", new MyNettyHandler());
    }
}
