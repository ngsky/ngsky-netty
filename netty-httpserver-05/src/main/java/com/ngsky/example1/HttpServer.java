package com.ngsky.example1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HttpServer {
    public static void main(String[] args) {
         EventLoopGroup bossGroup = new NioEventLoopGroup();
         EventLoopGroup workerGroup = new NioEventLoopGroup();

//         ServerBootstrap server = new ServerBootstrap();
//         server.group(bossGroup, workerGroup)
//                 .channel(NioSocketChannel.class)
//                 .

    }
}
