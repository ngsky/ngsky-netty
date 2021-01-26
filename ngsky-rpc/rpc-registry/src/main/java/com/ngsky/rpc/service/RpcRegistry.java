package com.ngsky.rpc.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RpcRegistry {
    private int port;

    public RpcRegistry(int port) {
        this.port = port;
    }

    public void start() {
        // selector
        EventLoopGroup boss = new NioEventLoopGroup();
        // work thread
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, work)
                    // 配置channel 通道
                    .channel(NioServerSocketChannel.class)
                    // 子处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 处理链
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 自定义协议解码器
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            // 自定义协议编码器
                            pipeline.addLast(new LengthFieldPrepender(4));
                            // 对象参数类型编码器
                            pipeline.addLast("encoder", new ObjectEncoder());
                            // 对象参数类型解码器
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                            // 自定义注册处理器
                            pipeline.addLast(new RpcRegistryHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 启动监听
            ChannelFuture future = server.bind(port).sync();
            System.out.println("RPC registry listener at : " + this.port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
