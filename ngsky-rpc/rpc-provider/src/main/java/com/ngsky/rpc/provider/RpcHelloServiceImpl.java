package com.ngsky.rpc.provider;

import com.ngsky.rpc.api.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {
    @Override
    public String hello(String name) {
        System.out.println("service provider, hello: " + name);
        return name + name;
    }
}
