package com.ngsky.rpc.consumer.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求获取服务列表请求体
 */
@Data
public class GetProtocol implements Serializable {
    // 0:请求获取服务列表
    private int type;
    // 客户端标识
    private String clientId;
}
