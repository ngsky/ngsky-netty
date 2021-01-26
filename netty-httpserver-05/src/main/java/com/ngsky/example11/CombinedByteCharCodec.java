package com.ngsky.example11;

import io.netty.channel.CombinedChannelDuplexHandler;

public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        // 将委托实例交给父类处理
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
