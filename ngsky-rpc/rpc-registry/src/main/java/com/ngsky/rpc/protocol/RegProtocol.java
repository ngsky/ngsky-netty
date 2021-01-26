package com.ngsky.rpc.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 传输数据包协议
 */
@Data
public class RegProtocol implements Serializable {
    // 0:注册服务
    private int type;
    // 服务名称
    private String serverName;
    // 类名
    private List<String> classNames;
}
