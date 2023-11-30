import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.openwire.v12.BaseDataStreamMarshaller;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class test {

    public static String fileToBase64(String filePath) {
        File file = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            fileInputStream.read(fileBytes);

            byte[] encodedBytes = Base64.getEncoder().encode(fileBytes);

            return new String(encodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\tmp\\Calc.class");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStream.read(bytes);




    }
}
