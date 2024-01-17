
package com.pacemrc.vuldebug.dubbo.consumer;

import com.pacemrc.dubbo.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboConsumer {

    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        context.start();

        DemoService demoService = (DemoService) context.getBean("demoService");
        String result = demoService.sayHello("hello");
        System.out.println(result);
    }
}
