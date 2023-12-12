package com.pacemrc.dubbo.impl;

import com.pacemrc.dubbo.api.DemoService;
import org.apache.dubbo.rpc.RpcContext;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name;
    }

}
