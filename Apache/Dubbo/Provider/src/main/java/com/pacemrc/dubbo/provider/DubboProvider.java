package com.pacemrc.dubbo.provider;

import com.rometools.rome.feed.impl.ToStringBean;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.common.utils.PojoUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.concurrent.CountDownLatch;
import org.apache.xbean.propertyeditor.JndiConverter;

public class DubboProvider {

    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-provider.xml");
        context.start();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
