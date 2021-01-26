package com.ngsky.example5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetAddress;

public class MyNettyHandler extends ChannelInboundHandlerAdapter {
    // 计数器
    private int count = 0;


    /**
     * 服务端接收到任意客户端数据 回调方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("接收到的数据:" + body + ";条数:" + (++count));

    }

    /**
     * 连接建立时 回调方法 channelActive
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端" + InetAddress.getLocalHost().getHostName() + "与服务端建立连接");
        super.channelActive(ctx);
    }
}
