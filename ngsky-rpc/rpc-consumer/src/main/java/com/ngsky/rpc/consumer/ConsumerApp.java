package com.ngsky.rpc.consumer;

import com.ngsky.rpc.consumer.handler.AutoGetServerHandler;
import com.ngsky.rpc.consumer.protocol.GetProtocol;
import com.ngsky.rpc.consumer.protocol.RProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Random;

public class ConsumerApp {
    public static void main(String[] args) {
        // 1.从注册中心获取服务列表
        ConsumerApp app = new ConsumerApp();
        RProtocol r = app.getServerList();


        // 2.调用服务
    }

    private RProtocol getServerList(){
        // 自动获取服务列表处理器
        AutoGetServerHandler autoGetServerHandler = new AutoGetServerHandler();
        // 封装请求数据
        GetProtocol getProtocol = new GetProtocol();
        getProtocol.setType(0);
        getProtocol.setClientId(String.valueOf(new Random(100).nextInt()));

        // 发起远程请求
        EventLoopGroup group = new NioEventLoopGroup();
        try{
           Bootstrap b= new Bootstrap();
           b.group(group)
                   .channel(NioSocketChannel.class)
                   .option(ChannelOption.TCP_NODELAY, true)
                   .handler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           ChannelPipeline pipeline = socketChannel.pipeline();
                           //自定义协议解码器
                           pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                           //自定义协议编码器
                           pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                           //对象参数类型编码器
                           pipeline.addLast("encoder", new ObjectEncoder());
                           //对象参数类型解码器
                           pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                           pipeline.addLast("handler", autoGetServerHandler);
                       }
                   });
           ChannelFuture future = b.connect("localhost", 8080).sync();
           future.channel().writeAndFlush(getProtocol).sync();
           future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
        return autoGetServerHandler.getR();
    }
}
