package com.ngsky.example3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    // channelGroup 用于保存客户端与服务端建立的连接Channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 接收消息，任意一个客户端发送过来的消息都将在 channelRead0 进行回调
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                // 发送消息的客户端非自己,那么将通知该客户端 有其它客户端正在发送信息
                ch.writeAndFlush(ch.remoteAddress() + " 发送消息:" + msg + "\n");
            } else {
                // 发送消息的客户端是自己
                ch.writeAndFlush("【自己】" + msg + "\n");
            }
        });
    }

    /**
     * 有新连接建立时 回调次 handlerAdded 方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 1.先通过所有已经建立连接的客户端
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + " 加入\n");

        // 2.将此连接客户端加入到ChannelGroup 中
        channelGroup.add(channel);
    }

    /**
     * 有连接断开时  回调 handlerRemoved 方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved start...:" + channelGroup.size());
        Channel channel = ctx.channel();
        // 1.先通过所有建立连接的客户端
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + " 离开\n");

        // 2.将此连接移除 channelGroup (注意：其实无需手动移除channelGroup, 因为netty会自动寻找上下文中的channelGroup，并移除下线的连接)
        // channelGroup.remove(channel);

        System.out.println("handlerRemoved end...:" + channelGroup.size());
    }

    /**
     * 连接处于上线状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线");
    }

    /**
     * 连接断开状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
