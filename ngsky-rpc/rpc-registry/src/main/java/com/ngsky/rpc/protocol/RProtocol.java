package com.ngsky.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class RProtocol implements Serializable {
    // 状态码: 0:成功, 1:失败
    private int code;
    // 响应信息
    private String msg;
    // 响应数据
    private Object data;
}
