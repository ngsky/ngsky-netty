package com.ngsky.rpc.consumer.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册服务响应数据包
 */
@Data
public class RProtocol implements Serializable {
    // 状态码: 0:成功, 1:失败
    private int code;
    // 响应信息
    private String msg;
    // 响应数据
    private Object data;
}
