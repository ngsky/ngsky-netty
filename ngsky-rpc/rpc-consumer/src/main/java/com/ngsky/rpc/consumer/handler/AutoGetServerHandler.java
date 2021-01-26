package com.ngsky.rpc.consumer.handler;

import com.ngsky.rpc.consumer.protocol.RProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AutoGetServerHandler extends ChannelInboundHandlerAdapter {

    private RProtocol r;

    public RProtocol getR() {
        return this.r;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.r = (RProtocol) msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("自动获取服务列表失败:" + cause.getMessage());
    }
}
