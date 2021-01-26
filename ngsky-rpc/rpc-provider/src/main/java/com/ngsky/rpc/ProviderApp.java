package com.ngsky.rpc;

import com.ngsky.rpc.protocol.RProtocol;
import com.ngsky.rpc.protocol.RegProtocol;
import com.ngsky.rpc.service.AutoRegHandler;
import com.ngsky.rpc.service.RpcProvider;
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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProviderApp {
    private List<String> fullClassNames = new ArrayList<>();

    public static void main(String[] args) {
        // 1.向注册中心注册服务
        new ProviderApp().register();

        // 2.启动自身服务
        new RpcProvider(9090).start();
    }

    private void register() {
        // 扫描服务包
        scanPkg("com.ngsky.rpc.provider");
        // 注册
        RProtocol r = doRegister();
        System.out.println("服务注册成功---> r:" + r.toString());
    }

    private void scanPkg(String scanPkg) {
        URL url = Objects.requireNonNull(this.getClass().getClassLoader().getResource(scanPkg.replaceAll("\\.", "/")));
        File dir = new File(url.getFile());
        if (dir.isDirectory()) {
            for (File f : Objects.requireNonNull(dir.listFiles())) {
                if (f.isDirectory()) {
                    scanPkg(scanPkg + "." + f.getName());
                } else {
                    fullClassNames.add(scanPkg + "." + f.getName().replace(".class", "").trim());
                }
            }
        }
    }

    private RProtocol doRegister() {
        // 自动注册服务处理器
        AutoRegHandler autoRegHandler = new AutoRegHandler();

        // 封装自身服务 注册信息
        RegProtocol regProtocol = new RegProtocol();
        regProtocol.setServerName("ufunc-server");
        regProtocol.setServerIp("localhost");
        regProtocol.setServerPort(9090);
        regProtocol.setType(0);
        regProtocol.setClassNames(fullClassNames);

        // 连接远程注册服务进行注册
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //自定义协议解码器
                            /** 入参有5个，分别解释如下
                             maxFrameLength：框架的最大长度。如果帧的长度大于此值，则将抛出TooLongFrameException。
                             lengthFieldOffset：长度字段的偏移量：即对应的长度字段在整个消息数据中得位置
                             lengthFieldLength：长度字段的长度：如：长度字段是int型表示，那么这个值就是4（long型就是8）
                             lengthAdjustment：要添加到长度字段值的补偿值
                             initialBytesToStrip：从解码帧中去除的第一个字节数
                             */
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            //自定义协议编码器
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                            //对象参数类型编码器
                            pipeline.addLast("encoder", new ObjectEncoder());
                            //对象参数类型解码器
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast("handler", autoRegHandler);
                        }
                    });
            ChannelFuture future = b.connect("localhost", 8080).sync();

            future.channel().writeAndFlush(regProtocol).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return autoRegHandler.getR();
    }
}
