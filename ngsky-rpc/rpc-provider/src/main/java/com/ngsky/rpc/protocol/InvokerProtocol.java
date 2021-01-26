package com.ngsky.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务调用 数据包
 */
@Data
public class InvokerProtocol implements Serializable {
    // 权限类名
    private String fullClassName;
    // 类名
    private String className;
    // 方法名称
    private String methodName;
    // 形参列表
    private Class<?>[] parames;
    // 实参数据
    private Object[] values;
}
