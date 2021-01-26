package com.ngsky.rpc.api;

/**
 * 加减乘除 操作
 */
public interface IRpcService {
    int add(int a, int b);

    int sub(int a, int b);

    int multiply(int a, int b);

    int divide(int a, int b);
}
