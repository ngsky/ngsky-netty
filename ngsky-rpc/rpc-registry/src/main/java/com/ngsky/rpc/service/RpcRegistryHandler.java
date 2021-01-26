package com.ngsky.rpc.service;

import com.ngsky.rpc.protocol.RProtocol;
import com.ngsky.rpc.protocol.RegProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RpcRegistryHandler extends ChannelInboundHandlerAdapter {
    /**
     * 保存所有相关的服务类
     */
    private ConcurrentHashMap<String, List<String>> serverMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 这也应该定义响应传输协议
        RProtocol r = new RProtocol();
        RegProtocol req = (RegProtocol) msg;
        // 注册服务
        if (0 == req.getType()) {
            serverMap.put(req.getServerName(), req.getClassNames());
            r.setCode(0);
            r.setMsg("成功");
        } else {
            r.setCode(1);
            r.setMsg("请求类型错误");
        }
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
