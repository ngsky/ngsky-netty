package com.ngsky.rpc.service;

import com.ngsky.rpc.protocol.InvokerProtocol;
import com.ngsky.rpc.protocol.RProtocol;
import com.ngsky.rpc.protocol.RegProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RpcProviderHandler extends ChannelInboundHandlerAdapter {
    /**
     * 保存所有相关的服务类
     */
    private ConcurrentHashMap<String, List<String>> serverMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RProtocol r = new RProtocol();
        InvokerProtocol request = (InvokerProtocol) msg;
        // 当客户端建立连接时，需要从自定义协议中获取信息
        Class<?> clazz = Class.forName(request.getFullClassName());
        Class<?> i = clazz.getInterfaces()[0];
        Object instance = i.newInstance();
        Method method = instance.getClass().getMethod(request.getMethodName(), request.getParames());
        Object o = method.invoke(instance, request.getValues());
        r.setCode(0);
        r.setMsg("ok");
        r.setData(o);

        ctx.write(r);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
