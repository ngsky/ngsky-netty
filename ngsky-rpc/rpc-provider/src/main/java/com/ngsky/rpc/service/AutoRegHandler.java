package com.ngsky.rpc.service;

import com.ngsky.rpc.protocol.RProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AutoRegHandler extends ChannelInboundHandlerAdapter {
    private RProtocol r;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        r = (RProtocol) msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server register is error!");
    }

    public RProtocol getR() {
        return this.r;
    }
}
