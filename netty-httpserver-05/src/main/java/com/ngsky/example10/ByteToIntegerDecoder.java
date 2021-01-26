package com.ngsky.example10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ByteToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 检查至少为 4 字节，再读取一个 int 值(4字节)
        if (in.readableBytes() >= 4) {
            out.add(in.readInt());
        }
    }
}
