package com.pacemrc.vuldebug.common.utils.http;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpRequest {

    private HttpClient httpClient;

    public HttpRequest(String proxyHost, int proxyPort){

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        httpClientBuilder.setDefaultRequestConfig(config);
        this.httpClient = httpClientBuilder.build();
    }

    public HttpRequest() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    public String sendGet(HttpGet httpGet) throws IOException {

        HttpResponse response = this.httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, StandardCharsets.UTF_8) : null;

        return responseBody;


    }

    private String exec(HttpPost httpPost) throws IOException {
        HttpResponse response = this.httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, StandardCharsets.UTF_8) : null;

        return responseBody;
    }

    public String sendByteArrayPost(HttpPost postRequest, byte[] bytes) throws IOException {

        ByteArrayEntity entity = new ByteArrayEntity(bytes);
        postRequest.setEntity(entity);
        String responseBody = this.exec(postRequest);

        return responseBody;
    }

    public String sendStringPost(HttpPost postRequest, String postBody) throws IOException {

        StringEntity stringEntity = new StringEntity(postBody);
        postRequest.setEntity(stringEntity);
        String responseBody = this.exec(postRequest);

        return responseBody;

    }
    public String sendFormFilePost(HttpPost postRequest, String filePath) throws IOException {

        File file = new File(filePath);
        String fileName = file.getName();

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(file, ContentType.DEFAULT_BINARY, fileName))
                .addPart("description", new StringBody("File description", ContentType.TEXT_PLAIN));
        HttpEntity multipartEntity = multipartEntityBuilder.build();
        postRequest.setEntity(multipartEntity);
        String responseBody = this.exec(postRequest);

        return responseBody;
    }
}
