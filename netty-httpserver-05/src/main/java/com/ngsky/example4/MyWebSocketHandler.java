package com.ngsky.example4;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalTime;

/**
 * TextWebSocketFrame : websocket 针对 文本数据传输对象 frame
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * channelRead0: 收到来自客户端发送的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("收到消息:" + channel.remoteAddress() + " \n " + msg.text());
        // 向客户端发送当前时间

        ctx.writeAndFlush(new TextWebSocketFrame("当前时间:" + LocalTime.now()));
    }

    /**
     * handlerAdded: 客户端与服务端建立连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端-" + ctx.channel().id().asLongText() + " 上线");
    }

    /**
     * handlerRemoved: 客户端与服务端断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端-" + ctx.channel().id().asLongText() + " 离开");
    }

    /**
     * 当发生任何异常时，需要关闭连接
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常");
        ctx.close();
    }
}
